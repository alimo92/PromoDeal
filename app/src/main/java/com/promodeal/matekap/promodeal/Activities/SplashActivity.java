package com.promodeal.matekap.promodeal.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

public class SplashActivity extends AppCompatActivity {

    int secondes;
    User CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        secondes =2;

        try {
            CurrentUser = AppController.getCurrentUser(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                if(CurrentUser==null){
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                }else{
                    Intent i = new Intent(SplashActivity.this, SearchActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, secondes * 1000);

    }
}
