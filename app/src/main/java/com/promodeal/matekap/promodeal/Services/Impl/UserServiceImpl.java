package com.promodeal.matekap.promodeal.Services.Impl;

import android.app.Activity;
import android.content.Context;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Services.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 14/04/2016.
 */
public class UserServiceImpl extends BasicServiceImpl<User> implements UserService {


    public UserServiceImpl(){
        urlBuilderImpl= new URLBuilderImpl();
    }

    @Override
    public JSONObject getJSON(User model) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("EmailUser", model.getEmailUser());
        object.put("PasswordUser", model.getPasswordUser());
        object.put("FirstNameUser", model.getFirstNameUser());
        object.put("LastNameUser", model.getLastNameUser());
        object.put("IdUser", model.getIdUser());
        object.put("ImageUser",model.getImageUser());
        return object;
    }

    @Override
    public User getModel(JSONObject object) throws JSONException {
        User model = new User();
        model.setIdUser(object.getInt("IdUser"));
        model.setFirstNameUser(object.getString("FirstNameUser"));
        model.setLastNameUser(object.getString("LastNameUser"));
        model.setEmailUser(object.getString("EmailUser"));
        model.setImageUser(object.getString("ImageUser"));
        //model.setDateCreated(Timestamp.valueOf(object.getString("DateCreated")));
        return model;
    }

    @Override
    public void Login(User account,final ServiceCallback<User> serviceCallback) throws JSONException {
        String URL=urlBuilderImpl.getURL("user","login");
        GetArrayResponse(URL, getJSON(account), new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                if(Response.length()>1){
                    User user = getModel(Response.getJSONObject(1));
                    serviceCallback.OnSuccess(user);
                }else{
                    serviceCallback.OnSuccess(null);
                }
            }
        });

    }

    @Override
    public void Register(User account, final ServiceCallback<User> serviceCallback) throws JSONException {
        String URL=urlBuilderImpl.getURL("user","register");
        GetArrayResponse(URL, getJSON(account), new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                if(Response.length()>1){
                    User user = getModel(Response.getJSONObject(1));
                    serviceCallback.OnSuccess(user);
                }else{
                    serviceCallback.OnSuccess(null);
                }
            }
        });
    }

    @Override
    public void SearchUser(String user,Activity activity, final ServiceCallbackList<User> serviceCallbackList) throws JSONException {
        String URL=urlBuilderImpl.getURL("user","search");
        JSONObject object = new JSONObject();
        object.put("Search",user);
        object.put("User", AppController.getCurrentUser(activity).getIdUser());
        GetArrayResponse(URL, object, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                List<User> list = new ArrayList<User>();
                for (int i = 1; i < Response.length(); i++) {
                    list.add(getModel(Response.getJSONObject(i)));
                }
                serviceCallbackList.OnSuccess(list);
            }
        });
    }

}
