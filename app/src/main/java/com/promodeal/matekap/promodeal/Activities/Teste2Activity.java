package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.promodeal.matekap.promodeal.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Teste2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste2);


        Map config = new HashMap();
        config.put("cloud_name", "promodeal");
        config.put("api_key", "142712279581164");
        config.put("api_secret", "iCqNru3MM2ndmKiX2OmBEeU11Fg");
        Cloudinary cloudinary = new Cloudinary(config);

        //cloudinary.uploader().upload(, ObjectUtils.emptyMap());



    }
}
