package Trunk;


import static Trunk.Constant.*;

import java.util.Scanner;
public class Register {

    public Register(){
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入账号：(最短4位)");
            String account = sc.nextLine();
            for (int i = 0; i < account.length(); i ++){
                if (account.length()<4){
                    System.out.println("账号长度太短！");
                    return;
                }
            }
            if (CURRENTUSER.confirmUser(account) && !account.equals("root")){
                System.out.println("请输入密码：(最短4位)");
                String password = sc.nextLine();
                if (password.length()<4){
                    System.out.println("密码长度太短！");
                    return;
                }
                if (CURRENTUSER.addUser(account,password))
                    DELAY();
                    System.out.println("注册成功！");
                return;
            }
        System.out.println("用户名已存在！");
    }
}
