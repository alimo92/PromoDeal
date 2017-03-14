package com.promodeal.matekap.promodeal.Services;

import android.app.Activity;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.User;

import org.json.JSONException;

/**
 * Created by Ali on 14/04/2016.
 */
public interface UserService {
    void Login(User account, final ServiceCallback<User> serviceCallback) throws JSONException;
    void Register(User account, final ServiceCallback<User> serviceCallback) throws JSONException;
    void SearchUser(String user,Activity activity,final ServiceCallbackList<User> userServiceCallbackList) throws JSONException;
}
