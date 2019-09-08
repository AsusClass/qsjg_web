package Bean;

public class Img {
    private String ext;
    private String name;
    private String url;
    private float size;
    private String date;

    public Img(String ext, String name, String url, float size, String date) {
        this.ext = ext;
        this.name = name;
        this.url = url;
        this.size = size;
        this.date = date;
    }

    public String getExt() {

        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
