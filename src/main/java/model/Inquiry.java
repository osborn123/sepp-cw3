package model;

import view.View;

import java.time.LocalDateTime; //imported from java.time package for LocalDateTime

public class Inquiry {
    private LocalDateTime createdAt;

    public String getInquirerEmail() {
        return inquirerEmail;
    }

    private String inquirerEmail;

    public String getSubject() {
        return subject;
    }

    private String subject;

    public String getContent() {
        return content;
    }

    private String content;

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    private String assignedTo;
    private SharedContext sharedContext;
    private View view;
   

  
    public Inquiry(String subject, String content, String inquirerEmail){
        this.createdAt = LocalDateTime.now(); //Contigent on localdatetime being the datatype
        this.subject = subject;
        this.content = content;
        this.inquirerEmail = inquirerEmail;
        this.assignedTo = null;
    }

    public Object getCreatedAt() {
        return this.createdAt;
    }
    }


