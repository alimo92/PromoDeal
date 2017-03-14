package com.promodeal.matekap.promodeal.Entities;

/**
 * Created by Ali on 02/06/2016.
 */
public class Rating extends BasicEntity {

    private int IdRating;
    private int RatingValue;
    private int IdPost;
    private User User;

    public Rating(int idRating, int ratingValue, int idPost, com.promodeal.matekap.promodeal.Entities.User user) {
        IdRating = idRating;
        RatingValue = ratingValue;
        IdPost = idPost;
        User = user;
    }

    public Rating() {
    }

    public int getIdRating() {
        return IdRating;
    }

    public void setIdRating(int idRating) {
        IdRating = idRating;
    }

    public int getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(int ratingValue) {
        RatingValue = ratingValue;
    }

    public int getIdPost() {
        return IdPost;
    }

    public void setIdPost(int idPost) {
        IdPost = idPost;
    }

    public com.promodeal.matekap.promodeal.Entities.User getUser() {
        return User;
    }

    public void setUser(com.promodeal.matekap.promodeal.Entities.User user) {
        User = user;
    }
}
