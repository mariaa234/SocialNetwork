package com.example.socialnetwork.domain;

public class Message {
   private String subject;
   private String content;
    private long userFrom;
    private long userTo;

    public Message(String subject, String content, long userFrom, long userTo) {
        this.subject = subject;
        this.content = content;
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUserFrom(long userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(long userTo) {
        this.userTo = userTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public long getUserFrom() {
        return userFrom;
    }

    public long getUserTo() {
        return userTo;
    }

}
