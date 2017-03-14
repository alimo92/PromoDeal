package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.promodeal.matekap.promodeal.Controllers.ConversationController;
import com.promodeal.matekap.promodeal.Controllers.MessageController;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.Message;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Listeners.MessageListener;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.ConversationServiceImpl;
import com.promodeal.matekap.promodeal.Services.Impl.UserServiceImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    User CurrentUser;
    Conversation conversation;
    User Receiver;

    MessageController messageController;
    ConversationController conversationController;

    Button Send;
    EditText Editmessage;
    Check check;
    volatile boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        Send =(Button) findViewById(R.id.send_message);
        Editmessage =(EditText)findViewById(R.id.edit_message);

        ConversationActivity.isRunning =false;

        messageController = new MessageController(this);
        conversationController = new ConversationController(this);

        try {
            CurrentUser = AppController.getCurrentUser(this);
            Receiver=getReceiver();
            conversation = getCurrentConversation();
            Editmessage.setHint("Write a message...");


            if(conversation!=null){
                messageController.GetConversationMessages(conversation.getIdConversation());
            }else if(Receiver!=null){
                conversation = new Conversation();
                List<User> templist = new ArrayList<>();
                templist.add(CurrentUser);
                templist.add(Receiver);
                conversationController.FindConversation(templist, conversation);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Check check = new Check();
        check.start();
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    messageController.setFlag(true);
                    if(Editmessage.getText().toString().equals("")==false){
                        Message message=null;
                        if(conversation!=null && conversation.getIdConversation()!=0){
                            message = CreateMessage(CurrentUser,conversation,Editmessage.getText().toString());
                            messageController.SendMessage(message,conversation);

                        }else if(Receiver!=null){
                            Log.e("Receiver", Receiver.getEmailUser().toString());
                            message = FirstMessage(CurrentUser,getReceiver(),Editmessage.getText().toString());
                            Log.e("Message",""+message.getReceivers().size());
                            messageController.SendFirstMessage(message,conversation);
                        }

                        Editmessage.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    Conversation getCurrentConversation() throws JSONException {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Conversation");
            if(value!=null){
                JSONObject object = new JSONObject(value);
                ConversationServiceImpl C = new ConversationServiceImpl();
                return C.getModel(object);
            }
            return null;
        }else{
            return null;
        }
    }

    User getReceiver() throws JSONException {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("Receiver");
            if(value!=null){
                JSONObject object = new JSONObject(value);
                UserServiceImpl C = new UserServiceImpl();
                return C.getModel(object);
            }
            return null;
        }else{
            return null;
        }
    }

    Message CreateMessage(User user, Conversation conversation,String content){
        Message message = new Message();
        List<User> receivers = new ArrayList<>();
        message.setUser(user);

        for(int i=0;i<conversation.getUsers().size();i++){
            if(conversation.getUsers().get(i).getEmailUser().equals(user.getEmailUser())==false){
                receivers.add(conversation.getUsers().get(i));
            }
        }
        message.setContentMessage(content);
        message.setReceivers(receivers);
        return message;
    }

    Message FirstMessage(User user,User receiver, String content){
        Message model = new Message();
        List<User> list = new ArrayList<>();
        model.setUser(user);
        list.add(receiver);
        model.setReceivers(list);
        model.setContentMessage(content);
        return model;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        running =false;

    }

    class Check extends Thread implements Runnable
    {
        public void run()
        {
            try {
                running =true;
                while(running){
                    Thread.sleep(2000);
                    if(conversation!=null && conversation.getIdConversation()!=0)
                        messageController.GetConversationMessages(conversation.getIdConversation());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
