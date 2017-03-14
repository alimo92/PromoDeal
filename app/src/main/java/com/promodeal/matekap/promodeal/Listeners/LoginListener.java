package com.promodeal.matekap.promodeal.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.promodeal.matekap.promodeal.Activities.RegisterActivity;
import com.promodeal.matekap.promodeal.Controllers.UserController;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

/**
 * Created by Ali on 21/04/2016.
 */
public class LoginListener implements View.OnClickListener {

    private UserController userController;
    private TextView RegisterAccount;
    private Activity activity;

    public LoginListener(Activity activity){
        this.activity=activity;
        this.userController = new UserController(activity);
        RegisterAccount =(TextView) activity.findViewById(R.id.RegisterAccount);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignInLogin:
                try {
                    userController.Login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.RegisterAccount:
                Intent intent = new Intent(activity, RegisterActivity.class);
                activity.startActivity(intent);
                break;
        }
    }
}
