package com.promodeal.matekap.promodeal.Services;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ali on 14/04/2016.
 */
public interface ConversationService {
    void getUserConversations(int iduser,ServiceCallbackList<Conversation> serviceCallbackList);
    void FindConversation(List<User> users,ServiceCallback<Conversation> serviceCallback) throws JSONException;
}
