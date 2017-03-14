package com.promodeal.matekap.promodeal.Entities;

/**
 * Created by Ali on 02/06/2016.
 */
public class Comment extends BasicEntity {

    private int IdComment;
    private User User;
    private int IdPost;
    private String ContentComment;

    public Comment(int idComment, com.promodeal.matekap.promodeal.Entities.User user, int idPost, String contentComment) {
        IdComment = idComment;
        User = user;
        IdPost = idPost;
        ContentComment = contentComment;
    }

    public Comment() {
    }

    public int getIdComment() {
        return IdComment;
    }

    public void setIdComment(int idComment) {
        IdComment = idComment;
    }

    public com.promodeal.matekap.promodeal.Entities.User getUser() {
        return User;
    }

    public void setUser(com.promodeal.matekap.promodeal.Entities.User user) {
        User = user;
    }

    public int getIdPost() {
        return IdPost;
    }

    public void setIdPost(int idPost) {
        IdPost = idPost;
    }

    public String getContentComment() {
        return ContentComment;
    }

    public void setContentComment(String contentComment) {
        ContentComment = contentComment;
    }


}
