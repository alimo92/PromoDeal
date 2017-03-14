package com.promodeal.matekap.promodeal.Services;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.Message;

import org.json.JSONException;

/**
 * Created by Ali on 18/04/2016.
 */
public interface MessageService {
    void SendMessage(Message message, ServiceCallback<Conversation> serviceCallback) throws JSONException;
    void GetConversationMessages(int idconversation,ServiceCallbackList<Message> serviceCallbackList) throws JSONException;
}
