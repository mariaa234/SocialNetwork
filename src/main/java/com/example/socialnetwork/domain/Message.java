package com.example.socialnetwork.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
   private String content;
    private long userFrom;
    private long userTo;
    private LocalDateTime date;

    public Message(String content, long userFrom, long userTo) {
        this.content = content;
        this.userFrom = userFrom;
        this.userTo = userTo;
        date=LocalDateTime.now();
    }

    public Message( String content, long userFrom, long userTo, String date) {
        this.content = content;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.date = LocalDateTime.parse(date);
    }


    public String getDate() {
        return date.toString();
    }
    public LocalDateTime getData(){return date;}

    public String getContent() {
        return content;
    }

    public long getUserFrom() {
        return userFrom;
    }

    public long getUserTo() {
        return userTo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
