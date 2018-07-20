package Bean;

import java.io.Serializable;

public class Block implements Serializable {

    private int id; //块id
    private String content;//块内容

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}
