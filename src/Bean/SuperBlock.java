package Bean;

import Trunk.TextEdit;
import static Trunk.Constant.*;

import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class SuperBlock implements Serializable {

    private ArrayList<Users> users = new ArrayList<>();//用户信息
    private ArrayList<Block> blocks = new ArrayList<>();//块信息
    private ArrayList<FileMsg> fileMsgs = new ArrayList<>();//文件信息

    private int bitmap[] = new int[FILEBLK];//位视图
    private int size; //剩余块
    private int num;//剩余用户数量

    public SuperBlock(){
        size = FILEBLK;
        num = USERNUM;
    } //初始化超级块

    public boolean addUser(String account, String password){

        Users user = new Users();
        user.setAccount(account);
        user.setPassword(password);
        users.add(user);
        num--;
        SAVE();
        return true;

    } //增加用户

    public Boolean confirmUser(String account){ //有false，无true
        for (Users user : users){
            if (user.getAccount().equals(account)){
                return false;
            }
        }
        return true;
    } //验证用户是否存在

    public boolean confirm(String account, String password){

        for (Users user : users) {
            if (user.getAccount().equals(account) && user.getPassword().equals(password)){
                ACCOUNT = account;
                PASSWORD = password;
                return true;
            }
        }
        return false;
    } //验证用户密码是否正确

    public void removeUser(){
        if (ACCOUNT.equals("root")){
            getUser();
            System.out.println("输入你要移除的用户！");
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();
            for (Users user : users){

                if (user.getAccount().equals(name)){
                    users.remove(user);
                    ArrayList<FileMsg> dFile = new ArrayList<>();
                    for (FileMsg fileMsg : fileMsgs){
                        if (fileMsg.getAccount().equals(name)){
                            dFile.add(fileMsg);
                            int ids[] = fileMsg.getidMsg();

                            for (int i = 0; i < ids.length; i ++){
                                bitmap[ids[i]] = 0;
                                size ++;
                            }
                            ArrayList <Block> b = new ArrayList<>();

                            for (int i = 0 ; i < ids.length; i ++){
                                for (Block block : blocks){
                                    if (block.getId() == ids[i]){
                                        b.add(block);
                                    }
                                }
                            }
                            for (Block block : b){
                                blocks.remove(block);
                            }
                        }
                    }

                    for (FileMsg d : dFile){
                        fileMsgs.remove(d);
                    }

                    SAVE();
                    num--;
                    System.out.println("删除成功！");
                    return;
                }

            }

        } else {
            System.out.println("无此权限！");
        }

    } //移除用户

    public void getUser(){
        for(Users user: users){
            System.out.println(user.getAccount());
        }
    } //列出所有用户

    //type 0 ,文件。 1 ，文件夹
    public void addMsg(int type,String name,String content){

        switch (type){

            case 0://文件

                write(name,content);
                break;

            case 1:

                name = name + "/";
                for (FileMsg ff : fileMsgs){
                    if (ff.getAccount().equals(ACCOUNT) && ff.getPath().startsWith(CURRENTPATH+name)){
                        System.out.println("该文件夹已存在！");
                        return;
                    }
                }
                write(name,"-1");
                break;
        }
    } //添加文件

    private void write(String name, String content){
        String part[] = new String[content.length()];

        int blockSize = (content.length())/FILESIZE + 1;
        int start = 0;
        int end = FILESIZE;
        int idMsg[] = new int[blockSize];
        for(int i = 0; i < blockSize; i ++){

            if(i == blockSize - 1){ // 判断是不是最后一块
                part[i] = content.substring(start);
                idMsg[i] = addBlock(part[i]);

            } else {
                part[i] = content.substring(start,end);
                start += FILESIZE;
                end += FILESIZE;
                idMsg[i] = addBlock(part[i]);
            }
        }

        Date now = new Date();
        DateFormat d1 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
        String date = d1.format(now);

        FileMsg fileMsg = new FileMsg();
        fileMsg.setPath(CURRENTPATH+name);
        fileMsg.setAccount(ACCOUNT);
        fileMsg.setIdMsg(idMsg);
        fileMsg.setDate(date);

        fileMsgs.add(fileMsg);
        size -= blockSize;
        SAVE();
        System.out.println("写入成功！");
    } //分配块及写入文件

    private int addBlock(String content){
        int id = 0;
        for (int i = 0; i < FILEBLK; i++ ){
            if (bitmap[i] == 0){
                id = i;
                break;
            }
        }
        Block block = new Block();
        block.setId(id);
        block.setContent(content);
        bitmap[id] = 1;
        blocks.add(block);
        SAVE();
        return id;
    } //分配块

    public void getBitmap(){
        for(int i = 1; i <= FILEBLK; i ++){
            if (bitmap[i - 1] == 0){
                System.out.print("."+" ");
            } else {
                System.out.print("*"+" ");
            }

            if (i%32 == 0){
                System.out.println();
            }
        }
    } //列出位视图

    public void getList(){

        ArrayList <String> list = new ArrayList<>();

        for(FileMsg fileMsg : fileMsgs){

            if(fileMsg.getAccount().equals(ACCOUNT) && fileMsg.getPath().startsWith(CURRENTPATH)){
                if (CURRENTPATH.equals("")){ //根目录
                    String s = fileMsg.getPath().split("/")[0];
                    if (!list.contains(s)){
                        String type;
                        if (isLegal(s)){
                            type = "<DIR>";
                        } else {
                            type = "     ";
                        }
                        list.add(s);
                        System.out.println(fileMsg.getDate()+ "    " + type + "    " + s);}
                }else {//次级目录
                    if (fileMsg.getPath().split(CURRENTPATH).length > 1){
                        String folder = fileMsg.getPath().split(CURRENTPATH)[1].split("/")[0];
                        if (!list.contains(folder)){
                            String type;
                            if (isLegal(folder)){
                                type = "<DIR>";
                            } else {
                                type = "     ";
                            }
                            list.add(folder);
                            System.out.println(fileMsg.getDate() + "    "+ type + "    " + folder);}
                    }

                }

            }
        }

        if (list.size() == 0){
            System.out.println("当前目录无文件！");
            return;
        }

    } //列出当前目录文件


    public void getMsg(String  name){

        int result[] = new int[20];
        String s = "";
        for(FileMsg fileMsg : fileMsgs){
            if (fileMsg.getPath().equals(CURRENTPATH + name) && fileMsg.getAccount().equals(ACCOUNT)){
                result = fileMsg.getidMsg();
            }
        }

        for(int i = 0; i < result.length; i ++){
            for (Block block : blocks){
                if (result[i] == block.getId()){
                    String p = block.getContent();
                    s += block.getContent();
                }
            }
        }

        new TextEdit(s,name);

        for (FileMsg fileMsg : fileMsgs){
            if (fileMsg.getPath().equals(name)){
                Remove(name);
                fileMsgs.remove(fileMsg);
                return;
            }
        }

    } //获取文件信息

    public void getFolder(String name){
       ff: if (name.equals("..")){

            String last[] = CURRENTPATH.split("/");

            int x = last.length;
            String la = "";
            for(int i = 0; i < x - 1; i++){
                la += last[i];
            }

            if (la.equals("")){
                CURRENTPATH = "";
                getList();
                return;

            }else {
                CURRENTPATH = la+"/";
            }

        } else {
            for (FileMsg fileMsg : fileMsgs){

                if(fileMsg.getAccount().equals(ACCOUNT) && fileMsg.getPath().startsWith(CURRENTPATH + name + "/")){
                    CURRENTPATH += (name+"/");
                    break ff;
                }

            }
            System.out.println("文件路径错误");
            return;
        }
            ArrayList <String> s = new ArrayList<>();


            for (FileMsg fileMsg : fileMsgs){
                if (fileMsg.getAccount().equals(ACCOUNT) && fileMsg.getPath().startsWith(CURRENTPATH)){
                    if(fileMsg.getPath().split(CURRENTPATH).length != 0){
                        String result[] = fileMsg.getPath().split(CURRENTPATH);

                        if(fileMsg.getPath().split(CURRENTPATH).length != 1 && !s.contains(result[1].split("/")[0])){
                            String add = result[1].split("/")[0];
                            String type;
                            if (isLegal(add)){
                                type = "<DIR>";
                            } else {
                                type = "     ";
                            }
                            s.add(add);
                            System.out.println(fileMsg.getDate() + "     "+ type+"     " + add);
                        }
                    }
                }
            }

    } //cd命令

    public void Remove(String name){


            if (!isLegal(name)){
                //删除文件
                ArrayList <FileMsg> rFile = new ArrayList<>();
                ArrayList <Block> b = new ArrayList<>();

                for (FileMsg fileMsg : fileMsgs){
                    if (fileMsg.getAccount().equals(ACCOUNT) && fileMsg.getPath().startsWith(CURRENTPATH + name)){
                        rFile.add(fileMsg);
                    }
                }


                for (FileMsg f : rFile){

                    int ids[] = f.getidMsg();//文件的块

                    for (int i = 0; i < ids.length; i ++){

                        bitmap[ids[i]] = 0; //初始化文件块
                        size++;
                        for (Block block : blocks){
                            if (block.getId() == ids[i]){
                                b.add(block);//收集要删除的块
                            }
                        }
                    }
                    fileMsgs.remove(f);//删除文件记录
                }

                for (Block block : b){//删除块
                    blocks.remove(block);
                }
                SAVE();

            } else {
                //删除文件夹
                ArrayList <FileMsg> rFile = new ArrayList<>();
                for (FileMsg fileMsg : fileMsgs){
                    if (fileMsg.getAccount().equals(ACCOUNT) && fileMsg.getPath().equals(CURRENTPATH + name+"/")){
                        int p[] = fileMsg.getidMsg();
                        ArrayList <Block> b = new ArrayList<>();
                        for (int i = 0 ; i < p.length; i ++){
                            bitmap[p[i]] = 0;
                            size++;
                        }

                        for (Block block : blocks){
                            for (int i = 0; i < p.length ; i ++){
                                if (block.getId() == p[i]){
                                    b.add(block);
                                }
                            }
                        }

                        for (Block block : b ){
                            blocks.remove(block);
                        }
                        rFile.add(fileMsg);

                    }
                }
                for (FileMsg f : rFile){
                    fileMsgs.remove(f);
                }
                SAVE();
                System.out.println("删除成功！");
            }

    } //移除文件

    public void Format(){

        ArrayList<FileMsg> dFile = new ArrayList<>();
        for (FileMsg fileMsg : fileMsgs){
            if (fileMsg.getAccount().equals(ACCOUNT)){
                dFile.add(fileMsg);
                int ids[] = fileMsg.getidMsg();

                for (int i = 0; i < ids.length; i ++){
                    bitmap[ids[i]] = 0;
                    size ++;
                }
                ArrayList <Block> b = new ArrayList<>();

                for (int i = 0 ; i < ids.length; i ++){
                    for (Block block : blocks){
                        if (block.getId() == ids[i]){
                            b.add(block);
                        }
                    }
                }
                for (Block block : b){
                    blocks.remove(block);
                }
            }
        }

        for (FileMsg d : dFile){
            fileMsgs.remove(d);
        }

        SAVE();

        CURRENTPATH = "";
    } //格式化

    public boolean hasName(String name) {
        for (FileMsg fileMsg : fileMsgs){
            if (fileMsg.getPath().equals(CURRENTPATH + name) && fileMsg.getAccount().equals(ACCOUNT)){
                return true;
            }
        }
        return false;
    } //判断是否有该文件

    public int getNum(){
        return num;
    } //获取剩余用户数量

    public int getSize(){
        return size;
    } //获取剩余块

    private boolean isLegal(String name) {
        for (int i = 0; i < name.length(); i++) {
            char a = name.charAt(i);
            if ((97 <= a && a <= 122) || (a <= 90 && a >= 65) || a >= 48 && a <= 57) {

            } else {
                return false;
            }

        }
        return true;
    }


}
