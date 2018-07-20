package Trunk;

import Bean.SuperBlock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Constant {

    public static  String ACCOUNT = ""; //当前用户名
    public static  String PASSWORD = "";//当前用户密码
    public static String CURRENTPATH = "";//当前路径

    public static SuperBlock CURRENTUSER = new SuperBlock();//当前超级快信息


    public static final int USERNUM = 10; //最大用户数量
    public static final int FILEBLK = 512;//总共的块数量
    public static final int FILESIZE = 32;//每个块包含的最大字符长度
    public static final int FILE_MAX_BLOCK = 3; // 文件最多块
    public static final String FILEPATH = "D:/msg.dat";//文件保存路径

    public static final String MKDIR = "mkdir"; //创建文件夹
    public static final String HELP = "help";//帮助
    public static final String CREATE = "create";//创建文件
    public static final String BITMAP = "bitmap";//位视图
    public static final String DIR = "dir";//列出当前目录
    public static final String Open = "open";//打开文件
    public static final String CD = "cd";//打开文件夹
    public static final String SIZE = "size";//文件块信息
    public static final String USERS = "users";//用户信息
    public static final String LOGOUT = "logout";//退出系统
    public static final String DELETE = "delete";//删除用户
    public static final String REMOVE = "remove";//移除文件
    public static final String FORMAT = "format";//格式化

    public static void DELAY(){ //。。。


            for(int i = 0; i < 2; i ++){
                System.out.print(".");
                Del(5);
            }
            System.out.println();



    }
    private static void Del(int x){
        for(int i = 0; i < x*100000000; i ++);
    }


    public static void SAVE(){
        try {
            File file = new File(FILEPATH);
            ObjectOutputStream ob;
            FileOutputStream fw = new FileOutputStream(file.getAbsoluteFile());
            ob = new ObjectOutputStream(fw);
            ob.writeObject(CURRENTUSER);
            ob.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //保存文件信息


}
