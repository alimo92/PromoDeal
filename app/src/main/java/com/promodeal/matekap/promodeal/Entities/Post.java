package com.promodeal.matekap.promodeal.Entities;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by Ali on 13/04/2016.
 */
public class Post extends BasicEntity{

    private int IdPost;
    private String TitlePost;
    private String DescriptionPost;
    private String ImagePost;
    private LocationPost LocationPost;
    private List<CategoryValue> categoryValueList;
    private User User;
    private List<Comment> Comments;
    private double AverageRating;
    private List<Rating> Ratings;
    private Bitmap BitmapPicture;

    public Post() {

    }

    public Post(int idPost, String titlePost, String descriptionPost, String imagePost, com.promodeal.matekap.promodeal.Entities.LocationPost locationPost) {
        IdPost = idPost;
        TitlePost = titlePost;
        DescriptionPost = descriptionPost;
        ImagePost = imagePost;
        LocationPost = locationPost;
    }

    public int getIdPost() {
        return IdPost;
    }

    public void setIdPost(int idPost) {
        IdPost = idPost;
    }

    public String getTitlePost() {
        return TitlePost;
    }

    public void setTitlePost(String titlePost) {
        TitlePost = titlePost;
    }

    public String getDescriptionPost() {
        return DescriptionPost;
    }

    public void setDescriptionPost(String descriptionPost) {
        DescriptionPost = descriptionPost;
    }

    public String getImagePost() {
        return ImagePost;
    }

    public void setImagePost(String imagePost) {
        ImagePost = imagePost;
    }

    public com.promodeal.matekap.promodeal.Entities.LocationPost getLocationPost() {
        return LocationPost;
    }

    public void setLocationPost(com.promodeal.matekap.promodeal.Entities.LocationPost locationPost) {
        LocationPost = locationPost;
    }

    public List<CategoryValue> getCategoryValueList() {
        return categoryValueList;
    }

    public void setCategoryValueList(List<CategoryValue> categoryValueList) {
        this.categoryValueList = categoryValueList;
    }

    public com.promodeal.matekap.promodeal.Entities.User getUser() {
        return User;
    }

    public void setUser(com.promodeal.matekap.promodeal.Entities.User user) {
        User = user;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }

    public double getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(double averageRating) {
        AverageRating = averageRating;
    }

    public List<Rating> getRatings() {
        return Ratings;
    }

    public void setRatings(List<Rating> ratings) {
        Ratings = ratings;
    }

    public Bitmap getBitmapPicture() {
        return BitmapPicture;
    }

    public void setBitmapPicture(Bitmap bitmapPicture) {
        BitmapPicture = bitmapPicture;
    }
}
