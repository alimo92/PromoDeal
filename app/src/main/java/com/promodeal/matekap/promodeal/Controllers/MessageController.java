package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Adapters.MessageAdapter;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.Message;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.MessageServiceImpl;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ali on 18/04/2016.
 */
public class MessageController {

    private boolean flag=true;
    TextView view;
    private MessageServiceImpl messageService;
    private MessageAdapter messageAdapter;
    private Context context;
    ListView listmessages;

    public MessageController(Activity activity){
        listmessages = (ListView)activity.findViewById(R.id.listview_message);
        messageService= new MessageServiceImpl();
        context =activity;
    }


    public void SendMessage(Message message, final Conversation conversation) throws JSONException {
        Log.e("HERE !!!","Step 1");
        messageService.SendMessage(message, new ServiceCallback<Conversation>() {
            @Override
            public void OnSuccess(Conversation Entity) throws JSONException {
                GetConversationMessages(Entity.getIdConversation());
                Log.e("HERE !!!", "Step 2");
                if(conversation.getIdConversation()==0){
                    conversation.setConversation(Entity);
                }
            }
        });
    }

    public void SendFirstMessage(Message message, final Conversation conversation) throws JSONException{
        messageService.SendMessage(message, new ServiceCallback<Conversation>() {
            @Override
            public void OnSuccess(Conversation Entity) throws JSONException {
                if(conversation.getIdConversation()==0){
                    conversation.setConversation(Entity);
                }
            }
        });
    }

    public void GetConversationMessages(int idconversation)throws JSONException{
        messageService.GetConversationMessages(idconversation, new ServiceCallbackList<Message>() {
            @Override
            public void OnSuccess(List<Message> list) {

                int index = listmessages.getFirstVisiblePosition();
                View v = listmessages.getChildAt(0);
                int top = (v == null) ? 0 : v.getTop();

                messageAdapter = new MessageAdapter((Activity) context,list);
                listmessages.setAdapter(messageAdapter);

                listmessages.setSelectionFromTop(index, top);

                if(flag==true){
                    listmessages.setSelection(messageAdapter.getCount() - 1);
                    flag=false;
                }
            }
        });
    }

    public boolean getFlag(){
        return flag;
    }

    public void setFlag(boolean a){
        flag=a;
    }
}
