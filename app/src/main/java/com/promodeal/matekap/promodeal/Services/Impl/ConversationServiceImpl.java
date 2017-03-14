package com.promodeal.matekap.promodeal.Services.Impl;

import android.nfc.Tag;
import android.util.Log;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Services.ConversationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 14/04/2016.
 */
public class ConversationServiceImpl extends BasicServiceImpl<Conversation> implements ConversationService {

    private UserServiceImpl userService;

    public ConversationServiceImpl(){
        this.userService= new UserServiceImpl();
        urlBuilderImpl= new URLBuilderImpl();
    }

    @Override
    public JSONObject getJSON(Conversation model) throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(int i=0;i<model.getUsers().size();i++) {
            array.put(userService.getJSON(model.getUsers().get((i))));
        }
        object.put("IdConversation", model.getIdConversation());
        object.put("DateCreated", model.getDateCreated());
        object.put("NameConversation", model.getNameConversation());
        object.put("Users",array);
        return object;
    }

    @Override
    public Conversation getModel(JSONObject object) throws JSONException {
        Conversation model = new Conversation();
        List<User> list = new ArrayList<>();
        JSONArray array = object.getJSONArray("Users");

        model.setNameConversation(object.getString("NameConversation"));
        model.setIdConversation(object.getInt("IdConversation"));
        model.setDateCreated(Timestamp.valueOf(object.getString("DateCreated")));

        for(int i=0;i<array.length();i++){
            JSONObject temp = array.getJSONObject(i);
            User user= userService.getModel(temp);
            list.add(user);
        }

        model.setUsers(list);
        return model;
    }

    @Override
    public void getUserConversations(int iduser, final ServiceCallbackList<Conversation> serviceCallbackList) {
        String URL = urlBuilderImpl.getURL("user", iduser+"/conversation/");
        GetArrayResponse(URL, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                List<Conversation> list = new ArrayList<Conversation>();
                for(int i=1;i<Response.length();i++){
                    JSONObject object = Response.getJSONObject(i);
                    list.add(getModel(object));
                }
                serviceCallbackList.OnSuccess(list);
            }
        });

    }

    @Override
    public void FindConversation(List<User> users, final ServiceCallback<Conversation> serviceCallback) throws JSONException {
        String URL = urlBuilderImpl.getURL("user","conversation");
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(int i=0;i<users.size();i++){
            JSONObject temp = new JSONObject();
            temp.put("User",users.get(i).getIdUser());
            array.put(temp);
        }
        object.put("Users",array);
        GetArrayResponse(URL, object, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                if (Response.length() > 1) {
                    JSONObject o = Response.getJSONObject(1);
                    Conversation conversation = getModel(o);
                    serviceCallback.OnSuccess(conversation);
                } else {
                    serviceCallback.OnSuccess(null);
                }
            }
        });
    }
}
