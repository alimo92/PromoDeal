package com.promodeal.matekap.promodeal.Entities;

import java.util.List;

/**
 * Created by Ali on 13/04/2016.
 */
public class Message extends BasicEntity{
    private int IdMessage;
    private String ContentMessage;
    private User User;
    private Conversation Conversation ;
    private List<User> Receivers;

    public Message(int idMessage, String contentMessage, User user,Conversation conversation, List<User> receivers) {
        IdMessage = idMessage;
        ContentMessage = contentMessage;
        User = user;
        Conversation = conversation;
        Receivers = receivers;
    }

    public Message() {
    }

    public int getIdMessage() {
        return IdMessage;
    }

    public void setIdMessage(int idMessage) {
        IdMessage = idMessage;
    }

    public String getContentMessage() {
        return ContentMessage;
    }

    public void setContentMessage(String contentMessage) {
        ContentMessage = contentMessage;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Conversation getConversation() {
        return Conversation;
    }

    public void setConversation(Conversation conversation) {
        Conversation = conversation;
    }

    public List<com.promodeal.matekap.promodeal.Entities.User> getReceivers() {
        return Receivers;
    }

    public void setReceivers(List<com.promodeal.matekap.promodeal.Entities.User> receivers) {
        Receivers = receivers;
    }
}
