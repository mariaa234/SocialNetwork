package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.repo.MessageRepoBD;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceHandleMessages {
    private MessageRepoBD messageRepoBD;

    public ServiceHandleMessages(MessageRepoBD messageRepoBD) {
        this.messageRepoBD = messageRepoBD;
    }

    public void addNewMessage(Message message) {
        messageRepoBD.save(message);
    }

    public Set<Message> findMessege(Long id1, Long id2) {
        Iterable<Message> messageIterable = messageRepoBD.findAll();
        Set<Message> msg = new HashSet<>();
        for (Message message : messageIterable)
            if ((id2 == message.getUserTo() && id1 == message.getUserFrom()) || (id1 == message.getUserTo() && id2 == message.getUserFrom()))
                msg.add(message);
        msg.stream().sorted(Comparator.comparing(Message::getData));
        return msg;
    }

}

