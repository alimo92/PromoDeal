package com.promodeal.matekap.promodeal.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.promodeal.matekap.promodeal.Controllers.UserController;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Listeners.LoginListener;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{

    Button SignIn;
    LoginButton facebookButton;
    TextView RegisterAccount;
    CallbackManager callbackManager;

    UserController userController ;
    User CurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.login);


        try {
            CurrentUser = AppController.getCurrentUser(this);
            if(CurrentUser!=null){
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LoginManager.getInstance().logOut();
        userController = new UserController(this);

        facebookButton = (LoginButton) findViewById(R.id.facebook_button);


        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");
        facebookButton.setReadPermissions(permissions);

        RegisterAccount =(TextView) findViewById(R.id.RegisterAccount);
        RegisterAccount.setOnClickListener(new LoginListener(this));

        SignIn =(Button) findViewById(R.id.SignInLogin);
        SignIn.setOnClickListener(new LoginListener(this));

        callbackManager = CallbackManager.Factory.create();

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {

                            String id = object.getString("id");
                            //Log.e("IDUSER",id);
                            String  imageURL ="https://graph.facebook.com/" + id + "/picture?type=normal";
                            String password = object.getString("email")+object.getString("id");
                            object.put("ImageUser",imageURL);
                            object.put("password",password);
                            userController.RegisterLoginFacebook(GetFacebookUser(object));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d("Cancel", "CANCEL!!!!");

                Toast.makeText(LoginActivity.this, "CANCEL!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Error", "ERROR!!!");

                Toast.makeText(LoginActivity.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
            }
        };
        facebookButton.registerCallback(callbackManager, callback);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public User GetFacebookUser(JSONObject object) throws JSONException {
        User user = new User();
        user.setFirstNameUser(object.getString("first_name"));
        user.setLastNameUser(object.getString("last_name"));
        user.setEmailUser(object.getString("email"));
        user.setPasswordUser(object.getString("password"));
        user.setImageUser(object.getString("ImageUser"));
        return user;
    }


}
