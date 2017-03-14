package com.promodeal.matekap.promodeal.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.promodeal.matekap.promodeal.Adapters.ConversationAdapter;
import com.promodeal.matekap.promodeal.Controllers.ConversationController;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ConversationActivity extends AppCompatActivity {

    private ConversationController C;
    private int idUser;
    public static boolean isRunning =true;
    private User model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);

        try {
            model = AppController.getCurrentUser(this);
            C= new ConversationController(this);
            C.GetUserConversations(model.getIdUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CheckConversation check = new CheckConversation();
        check.start();
        ImageButton imageButton =(ImageButton) findViewById(R.id.search_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning =false;
                Intent i = new Intent(ConversationActivity.this,SearchActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        isRunning =false;
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning=true;
    }

    class CheckConversation extends Thread implements Runnable{
        public void run(){
           while(isRunning==true){
               try {
                   Thread.sleep(4000);
                   C.GetUserConversations(model.getIdUser());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

}
