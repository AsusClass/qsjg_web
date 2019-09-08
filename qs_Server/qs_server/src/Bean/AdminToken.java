package Bean;

public class AdminToken {
    private  int u_id;
    private String token;
    private long date;  //时间戳


    public AdminToken(int u_id, String token, long date) {
        this.u_id = u_id;
        this.token = token;
        this.date = date;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
