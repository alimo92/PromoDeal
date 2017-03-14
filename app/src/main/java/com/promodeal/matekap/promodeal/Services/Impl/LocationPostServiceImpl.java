package com.promodeal.matekap.promodeal.Services.Impl;

import android.app.Activity;

import com.promodeal.matekap.promodeal.Core.Map.GPSTracker;
import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.LocationPost;
import com.promodeal.matekap.promodeal.Services.LocationPostService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 02/05/2016.
 */
public class LocationPostServiceImpl extends BasicServiceImpl<LocationPost> implements LocationPostService {

    private URLBuilderImpl urlBuilder;

    public LocationPostServiceImpl (){
        urlBuilder = new URLBuilderImpl();
    }

    @Override
    public JSONObject getJSON(LocationPost model) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("NameLocation",model.getNameLocation());
        object.put("LatitudeLocation",model.getLatitudeLocation());
        object.put("LongitudeLocation",model.getLongitudeLocation());
        return object;
    }

    @Override
    public LocationPost getModel(JSONObject object) throws JSONException {
        LocationPost model = new LocationPost();
        model.setIdLocation(object.getInt("IdLocation"));
        model.setLongitudeLocation(object.getDouble("LongitudeLocation"));
        model.setLatitudeLocation(object.getDouble("LatitudeLocation"));
        model.setNameLocation(object.getString("NameLocation"));
        return model;
    }

    @Override
    public void CreateLocation(LocationPost model, final ServiceCallback<LocationPost> serviceCallback) throws JSONException {
        String URL = urlBuilder.getURL("location","add");
        GetArrayResponse(URL, getJSON(model), new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                serviceCallback.OnSuccess(null);
            }
        });
    }

    @Override
    public LocationPost getLocation() {
        return null;
    }
}
