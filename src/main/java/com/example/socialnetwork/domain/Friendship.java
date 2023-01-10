package com.example.socialnetwork.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Friendship extends Entity<Tuple<Long, Long>> {
    private long user1;
    private long user2;
    private String friendsFrom;
    private int status;
    private Long from;


    public Friendship(long user1, long user2, long from) {
        if (user1 < user2) {
            setId(new Tuple<>(user1, user2));
            this.user1 = user1;
            this.user2 = user2;
        } else {
            setId(new Tuple<>(user2, user1));
            this.user1 = user2;
            this.user2 = user1;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.friendsFrom = LocalDateTime.now().format(format);
        this.status = 0;
        this.from = from;
    }

    public Friendship(long user1, long user2, String friendsFrom, int status, long from) {
        if (user1 < user2) {
            setId(new Tuple<>(user1, user2));
            this.user1 = user1;
            this.user2 = user2;
        } else {
            setId(new Tuple<>(user2, user1));
            this.user1 = user2;
            this.user2 = user1;
        }
        this.friendsFrom = friendsFrom;
        this.status = status;
        this.from = from;
    }

    public long getUser1() {
        return user1;
    }

    public long getUser2() {
        return user2;
    }

    public String getStringFriendsFrom() {
        return friendsFrom;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "user1=" + user1 +
                ", user2=" + user2 +
                ", friendsFrom='" + friendsFrom + '\'' +
                ", status=" + status +
                ", from=" + from +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship friendship = (Friendship) o;
        return (user1 == friendship.user1 && user2 == friendship.user2) || (user1 == friendship.user2 && user2 == friendship.user1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }

}
