package edu.uoc.dpoo;

import java.util.Date;

/**
 * Message Class
 * @author Victor Alonso Garrigos
 * DPOO
 */
public class Message {

	//Definition of attributes
    private String subject;
    private String content;
    private MessageStatus status;
    private Date createdAt;

    //Constructor method
    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.createdAt = new Date();
        this.status = MessageStatus.NEW;
    }   

    /**
     * Methods SET and GET for the attributes subject, content, status and 
     * creation date in a public way
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}