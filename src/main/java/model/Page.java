package model;

public class Page {
    private String title;
    private String content;
    private boolean isPrivate;
    private SharedContext sharedContext;

    public Page(String title, String content, boolean isPrivate) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;

    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean private_bool) {
        isPrivate = private_bool;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
