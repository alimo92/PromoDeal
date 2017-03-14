package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.promodeal.matekap.promodeal.Core.Map.GPSTracker;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.LocationPostServiceImpl;

/**
 * Created by Ali on 26/05/2016.
 */
public class LocationPostController  {

    Activity activity;
    CheckBox getLocationCheckBox;
    LocationPostServiceImpl locationPostService;
    GPSTracker gpsTracker;

    double latitude;
    double longitude;

    public LocationPostController(Activity a){
        activity=a;
        getLocationCheckBox =(CheckBox) activity.findViewById(R.id.getlocation);
        locationPostService = new LocationPostServiceImpl();
        gpsTracker = new GPSTracker(activity);
    }

    public void GetCurrentLocation(){
       getLocationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   // check if GPS enabled
                   if(GPSTracker.canGetLocation()){

                       latitude = GPSTracker.getLatitude();
                       longitude = GPSTracker.getLongitude();

                   }else{
                       // can't get location
                       // GPS or Network is not enabled
                       // Ask user to enable GPS/network in settings
                       GPSTracker.showSettingsAlert();
                   }
                   Toast.makeText(activity,"latitude="+latitude +"  longitude="+longitude,Toast.LENGTH_SHORT).show();
               }
           }
       });

    }


}
