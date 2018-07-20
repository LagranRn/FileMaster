package Trunk;

import java.util.Scanner;

import static Trunk.Constant.*;

public class Welcome {

    public Welcome(){
        Scanner sc = new Scanner(System.in);
        Size();
        System.out.print(ACCOUNT + "/>:");
        do {
            String op = sc.nextLine().trim();
            String ca[] = op.split(" ");
            try {
                switch (ca[0].trim().toLowerCase()){
                    case HELP:
                        Help();
                        break;
                    case CREATE:
                        Create(ca[1]);
                        break;
                    case BITMAP:
                        Bitmap();
                        break;
                    case DIR:
                        Dir();
                        break;
                    case MKDIR:
                        Mkdir(ca[1]);
                        break;
                    case Open:
                        Open(ca[1]);
                        break;
                    case CD:
                        Cd(ca[1]);
                        break;
                    case SIZE:
                        Size();
                        break;
                    case USERS:
                        Users();
                        break;
                    case REMOVE:
                        Remove(ca[1]);
                        break;
                    case LOGOUT:
                        CURRENTPATH = "";
                        return;
                    case DELETE:
                        Delete();
                        break;
                    case FORMAT:
                        Format();
                        break;
                    default:
                        System.out.println("指令错误！");
                        break;
                }
                System.out.print(ACCOUNT + "/>:" + " " + CURRENTPATH);

            } catch (Exception e) {
                System.out.println("请输入正确的指令！");
            }
        } while (true);
    }

    private void Help(){
        System.out.println("==================================================");
        System.out.println(

                        "创建目录:       <mkdir>    <文件夹>\n" +
                        "打开目录:       <cd>       <文件夹>\n" +
                        "创建文件:       <create>   <文件名>\n" +
                        "打开文件:       <open>     <文件名>\n" +
                        "删除文件:       <remove>   <文件名>\n" +
                        "改变目录:       <cd>       <文件夹名>\n" +
                        "列出文件目录:   <dir>\n" +
                        "退出:          <logout>\n" +
                        "格式化:        <format>\n" +
                         "外存占用:       <size>" );
        System.out.println("==================================================");
    }
    private void Create(String name){

            if (isLegal(name.split("\\.")[0]) && isLegal(name.split("\\.")[1])){

                if (CURRENTUSER.hasName(name)){
                    System.out.println("该文件已存在，是否需要更改？(y/n)");
                    Scanner sc = new Scanner(System.in);
                    switch (sc.nextLine()){
                            case "y":
                                    CURRENTUSER.Remove(name);
                                    System.out.println("已确认！");
                                    new TextEdit("",name);
                                     return;


                            case "n":
                                     System.out.println("已取消！");
                                    return;


                                 default:
                                    System.out.println("小心手滑！");
                                    return;
                    }
                }
                new TextEdit("",name);
                return;
            }

        System.out.println("文件名格式错误!");

    }
    private void Bitmap(){
        CURRENTUSER.getBitmap();
    }
    private void Dir(){
        CURRENTUSER.getList();
    }
    private void Mkdir(String name){

            if (!isLegal(name)){
                System.out.println("文件夹名错误！");
                return;
            }

        CURRENTUSER.addMsg(1,name,"-1");
    }
    private void Cd(String name){
        CURRENTUSER.getFolder(name);
    }
    private void Open(String name){

        if (CURRENTUSER.hasName(name)){
            CURRENTUSER.getMsg(name);
            return;
        }
        System.out.println("无此文件！");
    }
    private void Size(){
        System.out.println();
        System.out.println("系统共有"+FILEBLK+"个块");
        System.out.println("当前剩余" + CURRENTUSER.getSize()+"个空闲块");
        System.out.println();

    }
    private void Users(){
        CURRENTUSER.getUser();
    }
    private void Delete(){
        CURRENTUSER.removeUser();
    }
    private void Remove(String name) {
        CURRENTUSER.Remove(name);
    }
    private void Format(){
        System.out.println("是否确认格式化？？？(y/n)");
        Scanner sc = new Scanner(System.in);
        String op = sc.nextLine();
        switch (op){
            case "y":
                CURRENTUSER.Format();
                System.out.println("已格式化！");
                break;
            case "n":
                System.out.println("取消格式化！");
                break;
                default:
                    System.out.println("小心手滑！");
        }

    }

    private boolean isLegal(String name) {
        for (int i = 0; i < name.length(); i++) {
            char a = name.charAt(i);
            if ((97 <= a && a <= 122) || (a <= 90 && a >= 65) || (a >= 48 && a <= 57)) {

            } else {
                return false;
            }

        }
        return true;
    } //判断是否名字是否包含符号

}



