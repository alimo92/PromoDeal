package com.promodeal.matekap.promodeal.Services.Impl;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.Comment;
import com.promodeal.matekap.promodeal.Services.CommentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 02/06/2016.
 */
public class CommentServiceImpl extends BasicServiceImpl<Comment> implements CommentService {
    private UserServiceImpl userService;

    public CommentServiceImpl(){
        urlBuilderImpl = new URLBuilderImpl();
        userService = new UserServiceImpl();
    }

    @Override
    public JSONObject getJSON(Comment model) throws JSONException {
        JSONObject object = new JSONObject();

        object.put("User",model.getUser().getIdUser());
        object.put("Post",model.getIdPost());
        object.put("ContentComment",model.getContentComment());

        return object;
    }

    @Override
    public Comment getModel(JSONObject object) throws JSONException {
        Comment model = new Comment();

        model.setIdComment(object.getInt("IdComment"));
        model.setContentComment(object.getString("ContentComment"));
        model.setUser(userService.getModel(object.getJSONObject("User")));
        model.setIdPost(object.getInt("Post"));

        return model;
    }


    @Override
    public void SendComment(Comment model, final ServiceCallback<Comment> serviceCallback) throws JSONException {
        String URL = urlBuilderImpl.getURL("user/post","comment");
        GetArrayResponse(URL, getJSON(model), new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                serviceCallback.OnSuccess(null);
            }
        });
    }
}
