package com.promodeal.matekap.promodeal.Core.Volley;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.promodeal.matekap.promodeal.Entities.Post;
import com.promodeal.matekap.promodeal.Entities.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 13/04/2016.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;


    public Bitmap firstChoiceImg, secondChoiceImg;

    public User currentUser;
    private static AppController mInstance;
    public Bitmap cropped;
    private Post SelectedPost;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public  ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader == null){
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue,mLruBitmapCache);
        }
        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache(){
        if(mLruBitmapCache == null){
            mLruBitmapCache = new LruBitmapCache();
        }
        return this.mLruBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }



    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
            c.setDisplayOrientation(90);
//            apply initial settings
            Camera.Parameters parameters = c.getParameters();
            parameters.setJpegQuality(50);
//            add all desired settings
            c.setParameters(parameters);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    public static User getCurrentUser(Activity activity) throws JSONException {
        User model = new User();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        final String json = mPrefs.getString("CurrentUser", "");
        if(json==""){
            return null;
        }else{
            JSONObject object = new JSONObject(json);
            model.setIdUser(object.getInt("IdUser"));
            model.setFirstNameUser(object.getString("FirstNameUser"));
            model.setLastNameUser(object.getString("LastNameUser"));
            model.setEmailUser(object.getString("EmailUser"));
            model.setImageUser(object.getString("ImageUser"));
            return model;
        }
    }

    public static void RemoveCurrentUser(Activity activity){
        SharedPreferences mPrefs;
        SharedPreferences.Editor Edit;
        mPrefs= PreferenceManager.getDefaultSharedPreferences(activity);
        Edit=mPrefs.edit();
        Edit.remove("CurrentUser");
        Edit.commit();
    }

    public void setSelectedPost(Post p){
        this.SelectedPost = p;
    }

    public Post getSelectedPost(){
        return this.SelectedPost;
    }

}