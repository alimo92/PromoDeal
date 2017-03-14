package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.promodeal.matekap.promodeal.Controllers.UserController;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity {

    UserController userController;
    EditText search;

    boolean SearchFlag;
    int time;
    WaitMoment waitMoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchuser);
        SearchFlag=false;
        search =(EditText) findViewById(R.id.user_search);
        userController = new UserController(this);
        waitMoment = new WaitMoment();
        waitMoment.start();

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(search.getText().toString().equals("")){
                    userController.ClearSearch();
                }
                time=0;
            }
        });
    }
    class WaitMoment extends Thread implements Runnable
    {
        public void run()
        {
            SearchFlag=true;
            while(SearchFlag){
                try {
                    Thread.sleep(500);
                    time+=500;
                    if(time==2000){
                        if(!search.getText().toString().equals("")){
                            userController.SearchUser(search.getText().toString());
                        }
                        time+=1;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

