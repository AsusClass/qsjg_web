package Bean;
/*
新闻(公告）
 */
public class News {
    private String n_id;
    private String n_type;
    private String n_title;
    private String n_author ;
    private String n_date ;
    private int looked_times;
    private String n_content;

    public String getN_type() {
        return n_type;
    }

    public void setN_type(String n_type) {
        this.n_type = n_type;
    }

    private String n_imgs;
    private String fu_jian ;

    public News(String n_type,String n_title, String n_author, String n_date, int looked_times, String n_content, String n_imgs, String fu_jian) {

        this.n_type = n_type;
        this.n_title = n_title;
        this.n_author = n_author;
        this.n_date = n_date;
        this.looked_times = looked_times;
        this.n_content = n_content;
        this.n_imgs = n_imgs;
        this.fu_jian = fu_jian;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_author() {
        return n_author;
    }

    public void setN_author(String n_author) {
        this.n_author = n_author;
    }

    public String getN_date() {
        return n_date;
    }

    public void setN_date(String n_date) {
        this.n_date = n_date;
    }

    public int getLooked_times() {
        return looked_times;
    }

    public void setLooked_times(int looked_times) {
        this.looked_times = looked_times;
    }

    public String getN_content() {
        return n_content;
    }

    public void setN_content(String n_content) {
        this.n_content = n_content;
    }

    public String getN_imgs() {
        return n_imgs;
    }

    public void setN_imgs(String n_imgs) {
        this.n_imgs = n_imgs;
    }

    public String getFu_jian() {
        return fu_jian;
    }

    public void setFu_jian(String fu_jian) {
        this.fu_jian = fu_jian;
    }
}
