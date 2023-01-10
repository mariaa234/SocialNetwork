package com.example.socialnetwork.service;

import com.example.socialnetwork.domain.Friendship;
import com.example.socialnetwork.domain.Message;
import com.example.socialnetwork.repo.MessageRepoBD;

import java.util.HashSet;
import java.util.Set;

public class ServiceHandleMessages {
    private MessageRepoBD messageRepoBD;

    public ServiceHandleMessages(MessageRepoBD messageRepoBD) {
        this.messageRepoBD = messageRepoBD;
    }

    public Set<Message> getAllMessagesToThisUser(Long userTo)
    {
        Set<Message> messages=new HashSet<>();
        Iterable<Message> messageIterable=messageRepoBD.findAll();
        for(Message message:messageIterable)
            if(userTo== message.getUserTo())messages.add(message);
        return messages;
    }
    public void addNewMessage(Message message)
    {
        messageRepoBD.save(message);
    }

    public String findMessege(Long id1, Long id2) {
        Iterable<Message> messageIterable=messageRepoBD.findAll();

        for(Message message:messageIterable)
            if(id2==message.getUserTo()&& id1== message.getUserFrom())return message.getContent();
        return "";
    }
}

