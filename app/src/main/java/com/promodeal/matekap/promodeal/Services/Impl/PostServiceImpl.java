package com.promodeal.matekap.promodeal.Services.Impl;

import android.util.Log;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.CategoryValue;
import com.promodeal.matekap.promodeal.Entities.Comment;
import com.promodeal.matekap.promodeal.Entities.LocationPost;
import com.promodeal.matekap.promodeal.Entities.Post;
import com.promodeal.matekap.promodeal.Entities.Rating;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Services.PostService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 01/06/2016.
 */
public class PostServiceImpl extends BasicServiceImpl<Post> implements PostService {

    LocationPostServiceImpl locationPostService;
    UserServiceImpl userService;
    CategoryValueServiceImpl categoryValueService;
    CommentServiceImpl commentService;
    RatingServiceImpl ratingService;

    public PostServiceImpl(){
        locationPostService = new LocationPostServiceImpl();
        urlBuilderImpl = new URLBuilderImpl();
        userService = new UserServiceImpl();
        categoryValueService = new CategoryValueServiceImpl();
        commentService = new CommentServiceImpl();
        ratingService = new RatingServiceImpl();
    }

    @Override
    public JSONObject getJSON(Post model) throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        object.put("TitlePost",model.getTitlePost());
        object.put("User",model.getUser().getIdUser());
        object.put("DescriptionPost",model.getDescriptionPost());
        object.put("ImagePost",model.getImagePost());
        object.put("Location",locationPostService.getJSON(model.getLocationPost()));

        for(int i=0;i<model.getCategoryValueList().size();i++){
            JSONObject temp = new JSONObject();
            temp.put("CategoryValue",model.getCategoryValueList().get(i).getIdCategoryValue());
            array.put(temp);
        }

        object.put("Tags",new JSONArray());

        object.put("CategoryValues",array);

        return object;
    }

    @Override
    public Post getModel(JSONObject object) throws JSONException {
        Post model = new Post();

        JSONArray CategoryValuesArray = object.getJSONArray("CategoryValues");
        JSONArray CommentsArray = object.getJSONArray("Comments");
        JSONArray RatingsArray = object.getJSONArray("Ratings");

        List<CategoryValue> categoryValueList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();
        List<Rating> ratingList = new ArrayList<>();

        model.setUser(userService.getModel(object.getJSONObject("User")));
        model.setTitlePost(object.getString("TitlePost"));
        model.setDescriptionPost(object.getString("DescriptionPost"));
        model.setImagePost(object.getString("ImagePost"));
        model.setIdPost(object.getInt("IdPost"));
        model.setAverageRating(object.getDouble("AverageRating"));
        model.setDateCreated(Timestamp.valueOf(object.getString("DateCreated")));

        for (int i = 0; i < CategoryValuesArray.length(); i++) {
            JSONObject tempobject = CategoryValuesArray.getJSONObject(i);
            JSONObject tempvalue = tempobject.getJSONObject("CategoryValue");
            categoryValueList.add(categoryValueService.getModel(tempvalue));
        }
        model.setCategoryValueList(categoryValueList);


        for(int i=0;i<CommentsArray.length();i++){
            JSONObject tempobject = CommentsArray.getJSONObject(i);
            Comment tempcomment = commentService.getModel(tempobject);
            commentList.add(tempcomment);
        }
        model.setComments(commentList);

        for(int i=0;i<RatingsArray.length();i++){
            JSONObject tempobject = RatingsArray.getJSONObject(i);
            Rating tempRating = ratingService.getModel(tempobject);
            ratingList.add(tempRating);
        }
        model.setRatings(ratingList);

        JSONObject tempLocation = object.getJSONObject("LocationPost");
        if (tempLocation.length() > 0){
            model.setLocationPost(locationPostService.getModel(object.getJSONObject("LocationPost")));
        }else {
            model.setLocationPost(new LocationPost());
        }
        return model;
    }


    @Override
    public void AddPost(Post model, final ServiceCallback<Post> serviceCallback) throws JSONException {
        String Url = urlBuilderImpl.getURL("post","add");
        GetArrayResponse(Url, getJSON(model), new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                serviceCallback.OnSuccess(null);
            }
        });
    }

    @Override
    public void GetAllPosts(final ServiceCallbackList<Post> serviceCallbackList) throws JSONException {
        String Url = urlBuilderImpl.getURL("post","");
        Log.e("URL",Url);
        GetArrayResponse(Url, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                List<Post> list = new ArrayList<>();
                if(Response.length()>1){
                    for(int i=1;i<Response.length();i++){
                        Post tempPost = getModel(Response.getJSONObject(i));
                        list.add(tempPost);
                    }
                }
                serviceCallbackList.OnSuccess(list);
            }
        });
    }


}
