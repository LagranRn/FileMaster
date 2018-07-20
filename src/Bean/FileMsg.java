package Bean;

import java.io.Serializable;

public class FileMsg implements Serializable {

    private String account;//文件所有者
    private String  path; //文件路径
    private int[] idMsg;//文件包含的块
    private String date;//文件最后一次修改的日期


    public void setPath(String path) {
        this.path = path;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIdMsg(int idMsg[]) {
        this.idMsg = idMsg;
    }

    public String getPath() {
        return path;
    }

    public String getAccount() {
        return account;
    }

    public int[] getidMsg() {
        return idMsg;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

}
