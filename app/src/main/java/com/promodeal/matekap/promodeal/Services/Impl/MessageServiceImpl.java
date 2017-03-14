package com.promodeal.matekap.promodeal.Services.Impl;

import android.util.Log;

import com.android.volley.toolbox.JsonObjectRequest;
import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.Message;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Services.MessageService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 18/04/2016.
 */
public class MessageServiceImpl extends BasicServiceImpl<Message> implements MessageService {
    private ConversationServiceImpl conversationService;

    public MessageServiceImpl(){
        urlBuilderImpl = new URLBuilderImpl();
        conversationService = new ConversationServiceImpl();
    }

    @Override
    public JSONObject getJSON(Message model) throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        List<User> list = model.getReceivers();

        for(int i=0;i<list.size();i++){
            array.put(new JSONObject().put("IdReceiver",list.get(i).getIdUser()));
        }

        object.put("ContentMessage",model.getContentMessage());
        object.put("User",model.getUser().getIdUser());
        object.put("MessageReceivers",array);

        return object;
    }

    @Override
    public Message getModel(JSONObject object) throws JSONException {
        Message model = new Message();
        User user = new User();
        Conversation conversation = new Conversation();

        user.setIdUser(object.getInt("User"));
        conversation.setIdConversation(object.getInt("Conversation"));

        model.setIdMessage(object.getInt("IdMessage"));
        model.setContentMessage(object.getString("ContentMessage"));
        model.setDateCreated(Timestamp.valueOf(object.getString("DateCreated")));
        model.setUser(user);
        model.setConversation(conversation);

        return model;
    }

    @Override
    public void SendMessage(Message message, final ServiceCallback<Conversation> serviceCallback) throws JSONException {
        String URL =urlBuilderImpl.getURL("conversation","message/add");
        JSONObject model = getJSON(message);
        Log.e("JSON",model.toString());
        GetArrayResponse(URL, model, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                if (Response.length() > 1)
                    serviceCallback.OnSuccess(conversationService.getModel(Response.getJSONObject(1)));
            }
        });
    }

    @Override
    public void GetConversationMessages(int idconversation, final ServiceCallbackList<Message> serviceCallbackList) throws JSONException {
        String URL =urlBuilderImpl.getURL("conversation",idconversation+"/message/");
        final List<Message> list = new ArrayList<>();
        GetArrayResponse(URL, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                for(int i=1;i<Response.length();i++){
                    JSONObject object = Response.getJSONObject(i);
                    list.add(getModel(object));
                }
                serviceCallbackList.OnSuccess(list);
            }
        });
    }
}
