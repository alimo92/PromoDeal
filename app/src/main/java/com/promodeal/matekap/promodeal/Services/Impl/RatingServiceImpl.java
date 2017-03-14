package com.promodeal.matekap.promodeal.Services.Impl;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Entities.Rating;
import com.promodeal.matekap.promodeal.Services.RantingService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 02/06/2016.
 */
public class RatingServiceImpl extends BasicServiceImpl<Rating> implements RantingService {

    private UserServiceImpl userService;

    public RatingServiceImpl(){
        urlBuilderImpl = new URLBuilderImpl();
        userService = new UserServiceImpl();
    }

    @Override
    public JSONObject getJSON(Rating model) throws JSONException {
        return null;
    }

    @Override
    public Rating getModel(JSONObject object) throws JSONException {
        Rating model = new Rating();

        model.setIdPost(object.getInt("Post"));
        model.setUser(userService.getModel(object.getJSONObject("User")));
        model.setIdRating(object.getInt("IdRating"));
        model.setRatingValue(object.getInt("RatingValue"));

        return model;
    }
}
