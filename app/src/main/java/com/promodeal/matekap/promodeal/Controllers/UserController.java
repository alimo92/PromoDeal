package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.promodeal.matekap.promodeal.Activities.ConversationActivity;
import com.promodeal.matekap.promodeal.Activities.MainActivity;
import com.promodeal.matekap.promodeal.Adapters.SearchAdapter;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.UserServiceImpl;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ali on 14/04/2016.
 */
public class UserController {

    EditText FirstNameUser;
    EditText LastNameUser;
    EditText PasswordUser;
    EditText ConfirmPasswordUser;
    EditText EmailUser;

    EditText PasswordLogin;
    EditText EmailLogin;

    TextView teste;
    TextView home;
    TextView result;
    TextView testehome;

    ListView listsearch;

    Activity activity;

    SharedPreferences mPrefs;
    SharedPreferences.Editor prefsEditor;

    private UserServiceImpl UserServiceImpl;
    SearchAdapter searchAdapter;

    public UserController(Activity activity){

        this.UserServiceImpl= new UserServiceImpl();
        this.activity=activity;

        FirstNameUser =(EditText) activity.findViewById(R.id.signUpFirstnameET);
        LastNameUser =(EditText) activity.findViewById(R.id.signUpLastnameET);
        PasswordUser =(EditText) activity.findViewById(R.id.signUpPasswordET);
        ConfirmPasswordUser =(EditText) activity.findViewById(R.id.signUpConfirmPasswordET);
        EmailUser =(EditText) activity.findViewById(R.id.signUpEmailET);
        teste= (TextView) activity.findViewById(R.id.teste);

        PasswordLogin =(EditText) activity.findViewById(R.id.PasswordLogin);
        EmailLogin =(EditText) activity.findViewById(R.id.EmailLogin);

        home=(TextView) activity.findViewById(R.id.testehome);
        result=(TextView) activity.findViewById(R.id.resultlogin);
        testehome=(TextView) activity.findViewById(R.id.testehome);

        listsearch =(ListView) activity.findViewById(R.id.result_search);

        mPrefs=PreferenceManager.getDefaultSharedPreferences(activity);
        prefsEditor= mPrefs.edit();
    }

    public void Login() throws JSONException {
        User account = GetDataLogin();
        UserServiceImpl.Login(account, new ServiceCallback<User>() {
            @Override
            public void OnSuccess(User Entity) throws JSONException {
                if (Entity == null) {
                    result.setText("Wroung Password");
                } else {
                    AddValueToSharedPreferences("CurrentUser", UserServiceImpl.getJSON(Entity).toString());
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
    }

    public void Register() throws JSONException {
        final User account = GetDataRegister();
        account.setImageUser("https://monstarlocation.files.wordpress.com/2015/01/sda_avatar_icon-jpg500.jpeg");
        UserServiceImpl.Register(account, new ServiceCallback<User>() {
            @Override
            public void OnSuccess(User Entity) throws JSONException {
                if (Entity == null) {
                    teste.setText("Email Already Used");
                } else {
                    AddValueToSharedPreferences("CurrentUser", UserServiceImpl.getJSON(Entity).toString());
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
    }

    public void RegisterLoginFacebook(final User account) throws JSONException{
        UserServiceImpl.Login(account, new ServiceCallback<User>() {
            @Override
            public void OnSuccess(User Entity) throws JSONException {
                if(Entity==null){
                    UserServiceImpl.Register(account, new ServiceCallback<User>() {
                        @Override
                        public void OnSuccess(User Entity) throws JSONException {
                            if (Entity == null) {
                                Toast.makeText(activity, "ERROR!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                AddValueToSharedPreferences("CurrentUser", UserServiceImpl.getJSON(Entity).toString());
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                        }
                    });
                }else{
                    AddValueToSharedPreferences("CurrentUser", UserServiceImpl.getJSON(Entity).toString());
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });
    }

    public void SearchUser(String user ) throws JSONException {
        UserServiceImpl.SearchUser(user, activity, new ServiceCallbackList<User>() {
            @Override
            public void OnSuccess(List<User> list) {
                searchAdapter = new SearchAdapter(activity, list);
                listsearch.setAdapter(searchAdapter);
            }
        });
    }

    User GetDataRegister(){
        User user = new User();
        user.setPasswordUser(PasswordUser.getText().toString());
        user.setEmailUser(EmailUser.getText().toString());
        user.setFirstNameUser(FirstNameUser.getText().toString());
        user.setLastNameUser(LastNameUser.getText().toString());
        return user;
    }

    User GetDataLogin(){
        User user = new User();
        user.setEmailUser(EmailLogin.getText().toString());
        user.setPasswordUser(PasswordLogin.getText().toString());
        return user;
    }

    void AddValueToSharedPreferences(String key,String value) throws JSONException{
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void ClearSearch(){
        listsearch.setAdapter(null);
    }

}
