package com.promodeal.matekap.promodeal.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.strategies.StrategyLoader;
import com.cloudinary.utils.ObjectUtils;
import com.promodeal.matekap.promodeal.Controllers.CategoryController;
import com.promodeal.matekap.promodeal.Controllers.LocationPostController;
import com.promodeal.matekap.promodeal.Controllers.PostController;
import com.promodeal.matekap.promodeal.Core.Map.GPSTracker;
import com.promodeal.matekap.promodeal.Entities.LocationPost;
import com.promodeal.matekap.promodeal.Entities.Post;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    CategoryController categoryController;
    LocationPostController locationPostController;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    String link_url=null;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri imgUri;

    private CheckBox checkBox;
    LocationPost locationPost;

    TextView messageLocation;
    EditText nameLocation;
    ImageButton imageButton;
    Button confirm;
    Bitmap bp;

    boolean running = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpost);

        categoryController = new CategoryController(this);
        categoryController.getCategories();

        checkBox =(CheckBox) findViewById(R.id.getlocation);

        nameLocation =(EditText) findViewById(R.id.name_location);
        messageLocation =(TextView) findViewById(R.id.message_location);
        confirm = (Button) findViewById(R.id.confirm_post);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){


                    GPSTracker GPSTracker = new GPSTracker(AddPostActivity.this);
                    String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    // check if GPS enabled
                    if(GPSTracker.canGetLocation() && provider!=null){
                        messageLocation.setVisibility(View.INVISIBLE);
                        nameLocation.setVisibility(View.VISIBLE);
                        double latitude = GPSTracker.getLatitude();
                        double longitude = GPSTracker.getLongitude();


                        locationPost = getLocationPost(longitude,latitude,nameLocation.getText().toString());


                        //Toast.makeText(AddPostActivity.this,"longitude"+locationPost.getLatitudeLocation(),Toast.LENGTH_LONG).show();
                        // \n is for new line
                    }else{
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        Toast.makeText(AddPostActivity.this,"Please Enable GPS",Toast.LENGTH_LONG).show();
                        checkBox.setChecked(false);
                        GPSTracker.showSettingsAlert();
                    }

                }else{
                    nameLocation.setVisibility(View.INVISIBLE);
                    messageLocation.setVisibility(View.VISIBLE);
                    nameLocation.setText("");
                    locationPost =null;
                    Toast.makeText(AddPostActivity.this,""+locationPost,Toast.LENGTH_LONG).show();
                }

            }
        });

        imageButton =(ImageButton)findViewById(R.id.take_picture_post);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
                imgUri = Uri.fromFile(pictureFile);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                //cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationPost.setNameLocation(nameLocation.getText().toString());

                Cloud c = new Cloud();
                c.start();

                UrlDone urlDone = new UrlDone();
                urlDone.start();

            }
        });

    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "PromoDeal");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,"start !! ",Toast.LENGTH_LONG).show();
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                bp = (Bitmap) data.getExtras().get("data");
                bp =RotateBitmap(bp,90);
                imageButton.setImageBitmap(bp);


                /*
                //Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                Uri capturedimage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(capturedimage,
                        filePathColumn, null, null, null);

                try {

                    cursor.moveToFirst();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgPath = cursor.getString(columnIndex);

                cursor.close();

                Intent intent = new Intent(this, CropImageActivity.class);
                intent.putExtra("EXTRA", imgPath);
                startActivityForResult(intent, 2);
                */
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this,"else if !! ",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,"else !! ",Toast.LENGTH_LONG).show();
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    class Cloud extends Thread implements Runnable
    {

        public void run()
        {
            try {
                Map config = new HashMap();
                config.put("cloud_name", "promodeal");
                config.put("api_key", "142712279581164");
                config.put("api_secret", "iCqNru3MM2ndmKiX2OmBEeU11Fg");
                Cloudinary cloudinary = new Cloudinary(config);

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                cloudinary.uploader().upload(getRealPathFromURI(getImageUri(AddPostActivity.this,bp)),ObjectUtils.asMap("public_id",timeStamp ));
                link_url = cloudinary.url().generate(timeStamp);
                //Toast.makeText(AddPostActivity.this,link_url,Toast.LENGTH_LONG).show();
                Log.e("HERE !!!!",link_url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LocationPost getLocationPost(double longitude, double latitude,String namelocation){
        LocationPost locationPost = new LocationPost();

        locationPost.setLatitudeLocation(latitude);
        locationPost.setLongitudeLocation(longitude);
        locationPost.setNameLocation(namelocation);

        return locationPost;
    }

    class UrlDone extends Thread implements Runnable
    {

        public void run()
        {
            while(running){
                if(link_url!=null){
                    PostController postController = new PostController(AddPostActivity.this,locationPost,categoryController.getListId(),link_url);
                    try {
                        Post currentpost =postController.CreatePost();
                        Log.e("HERE !!!!!",postController.getservice().getJSON(currentpost).toString());
                        postController.AddPost(currentpost);
                        running=false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
