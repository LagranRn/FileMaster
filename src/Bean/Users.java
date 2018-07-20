package Bean;

import java.io.Serializable;

public class Users implements Serializable {

    private String account;//用户名
    private String password;//用户密码

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }
}
