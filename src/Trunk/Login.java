package Trunk;


import java.util.Scanner;

import static Trunk.Constant.*;

public class Login {

    public Login(){
        String type;
        Scanner sc = new Scanner(System.in);
        System.out.println("===================");
        System.out.println("a.普通用户\n" +
                "b.管理员");
        System.out.println("===================");

        type = sc.nextLine().trim().toLowerCase();
        switch (type){
            case "a":
                Normal();
                break;
            case "b":
                Manager();
                break;
                default:
                    System.out.println("请输入正确的操作码！");
        }


    }
    private void Normal(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号:");
        String account = sc.nextLine();
        if (!CURRENTUSER.confirmUser(account)){
            System.out.println("请输入密码:");
            String password = sc.nextLine();
            if (CURRENTUSER.confirm(account,password)){
                DELAY();
                System.out.println("登录成功！");
                new Welcome();
                return;
            }
            System.out.println("密码错误！");
            return;
        }
        System.out.println("无此用户！");
    }
    private void Manager(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号：");
        String account = sc.nextLine();
        System.out.println("请输入密码");
        String password = sc.nextLine();
        if (account.equals("root") && password.equals("root")){
            ACCOUNT = account;
            new Welcome();
            return;
        }
        System.out.println("输入不正确！");
    }
}
