package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.promodeal.matekap.promodeal.Controllers.UserController;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;


public class RegisterActivity extends AppCompatActivity {


    Button SignUp;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        SignUp =(Button) findViewById(R.id.SignUpButton);
        userController = new UserController(this);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userController.Register();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
