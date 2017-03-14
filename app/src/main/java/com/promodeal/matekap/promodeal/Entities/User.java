package com.promodeal.matekap.promodeal.Entities;

/**
 * Created by Ali on 13/04/2016.
 */
public class User extends BasicEntity{

    private int IdUser;
    private String FirstNameUser;
    private String LastNameUser;
    private String EmailUser;
    private String PasswordUser;
    private String ImageUser;

    public User(int idUser, String firstNameUser, String lastNameUser, String emailUser, String passwordUser) {
        IdUser = idUser;
        FirstNameUser = firstNameUser;
        LastNameUser = lastNameUser;
        EmailUser = emailUser;
        PasswordUser = passwordUser;
    }

    public User() {
    }

    public int getIdUser() {
        return IdUser;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setPasswordUser(String passwordUser) {
        PasswordUser = passwordUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public String getFirstNameUser() {
        return FirstNameUser;
    }

    public void setFirstNameUser(String firstNameUser) {
        FirstNameUser = firstNameUser;
    }

    public String getLastNameUser() {
        return LastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        LastNameUser = lastNameUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getImageUser() {
        return ImageUser;
    }

    public void setImageUser(String imageUser) {
        ImageUser = imageUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "IdUser=" + IdUser +
                ", FirstNameUser='" + FirstNameUser + '\'' +
                ", LastNameUser='" + LastNameUser + '\'' +
                ", EmailUser='" + EmailUser + '\'' +
                ", DateCreated='" + DateCreated + '\'' +
                ", PasswordUser='" + PasswordUser + '\'' +
                '}';
    }
}
