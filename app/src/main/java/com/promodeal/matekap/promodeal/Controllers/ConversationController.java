package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Adapters.ConversationAdapter;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.ConversationServiceImpl;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ali on 14/04/2016.
 */
public class ConversationController {

    private ConversationServiceImpl conversationService;
    private MessageController messageController;
    private ConversationAdapter conversationAdapter;
    private Context context;
    private ListView listconversations;


    public ConversationController(Activity activity){
        this.conversationService = new ConversationServiceImpl();
        listconversations =(ListView) activity.findViewById(R.id.listview_conversation);
        context = activity;
        messageController = new MessageController(activity);
    }

    public void GetUserConversations(int iduser){
        conversationService.getUserConversations(iduser, new ServiceCallbackList<Conversation>() {
            @Override
            public void OnSuccess(List<Conversation> list) {
                conversationAdapter = new ConversationAdapter((Activity) context,list);
                listconversations.setAdapter(conversationAdapter);
            }
        });
    }

    public void FindConversation(List<User> users, final Conversation conversation) throws JSONException {
        conversationService.FindConversation(users, new ServiceCallback<Conversation>() {
            @Override
            public void OnSuccess(Conversation Entity) throws JSONException {
                if(Entity!=null){
                    conversation.setConversation(Entity);
                    messageController.GetConversationMessages(Entity.getIdConversation());
                }
            }
        });
    }
}
