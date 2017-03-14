package com.promodeal.matekap.promodeal.Entities;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Ali on 13/04/2016.
 */
public class Conversation extends BasicEntity {

    private int IdConversation;
    private List<User> Users;
    private String NameConversation;
    private List<Message> Messages;

    public Conversation(int idConversation, List<User> users, String nameConversation, List<Message> messages) {
        IdConversation = idConversation;
        Users = users;
        NameConversation = nameConversation;
        Messages = messages;
    }

    public Conversation() {
    }

    public int getIdConversation() {
        return IdConversation;
    }

    public void setIdConversation(int idConversation) {
        IdConversation = idConversation;
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }

    public String getNameConversation() {
        return NameConversation;
    }

    public void setNameConversation(String nameConversation) {
        NameConversation = nameConversation;
    }

    public List<Message> getMessages() {
        return Messages;
    }

    public void setMessages(List<Message> messages) {
        Messages = messages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "IdConversation=" + IdConversation +
                ", Users=" + getUsers().size() +
                ", NameConversation='" + NameConversation + '\'' +
                ", Messages=" + getMessages().size() +
                '}';
    }

    public void setConversation(Conversation c){
        this.IdConversation =c.getIdConversation();
        this.NameConversation =c.getNameConversation();
        this.Users = c.getUsers();
    }
}
