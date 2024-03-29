package model;

import java.time.LocalDateTime; //imported from java.time package for LocalDateTime

public class Inquiry {
    private LocalDateTime createdAt; 
    private String inquirerEmail;
    private String subject;
    private String content;
    private String assignedTo;
    public Inquiry(String inquirerEmail, String subject, String content){
        this.inquirerEmail = inquirerEmail;
        this.subject = subject;
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public String getInquirerEmail() {
        return this.inquirerEmail;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getContent() {
        return this.content;
    }

    public String getAssignedTo() {
        return this.assignedTo;
    }
}

