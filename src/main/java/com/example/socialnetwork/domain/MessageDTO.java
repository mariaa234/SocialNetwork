package com.example.socialnetwork.domain;

public class MessageDTO {
    private String from;
    private String subject;

    public MessageDTO(String userFrom, String subject) {
        from = userFrom;
        this.subject =subject;
    }


    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }
}
