package model;

public class Page {
    private String title;
    private String content;
    private boolean isPrivate;
    public Page(String title, String content, boolean isPrivate){
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
    }
}
