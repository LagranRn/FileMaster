package Trunk;

import Bean.SuperBlock;

import java.io.*;
import java.util.Scanner;

import static Trunk.Constant.FILEPATH;
import static Trunk.Constant.*;

public class FileSystem  {

    public FileSystem(){
        init();
    }


    private void init(){//初始化磁盘

        try {
            File file = new File(FILEPATH);
            if(!file.exists()){
                file.createNewFile();
                ObjectOutputStream ob;
                FileOutputStream fw = new FileOutputStream(file.getAbsoluteFile());
                ob = new ObjectOutputStream(fw);
                ob.writeObject(CURRENTUSER);
                ob.close();
                fw.close();
            }else {
                try {
                    ObjectInputStream ob;
                    FileInputStream fw = new FileInputStream(file.getAbsoluteFile());
                    ob = new ObjectInputStream(fw);
                    CURRENTUSER = (SuperBlock)ob.readObject();
                    fw.close();
                    ob.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    } //初始化文件信息

    public static void main(String[] args) {
        DELAY();
        new FileSystem();
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("===================");
            System.out.println("1.登录");
            System.out.println("2.注册");
            System.out.println("===================");
            String op = sc.nextLine();
            switch (op){

                case "1":
                    new Login();
                    break;

                case "2":
                    if (CURRENTUSER.getNum() == 0){
                        System.out.println("用户已达最大值！");
                    } else {
                        new Register();
                    }

                    break;

                    default:
                        System.out.println("请输入正确的操作码！");
            }
        } while (true);
    }
}
