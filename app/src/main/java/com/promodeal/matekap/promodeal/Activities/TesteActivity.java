package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.promodeal.matekap.promodeal.Core.Map.GPSTracker;
import com.promodeal.matekap.promodeal.R;


public class TesteActivity extends AppCompatActivity {
    TextView Location;
    Button LocationButton;
    GPSTracker GPSTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);

        LocationButton =(Button) findViewById(R.id.locationbutton);
        Location =(TextView) findViewById(R.id.location);

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker = new GPSTracker(TesteActivity.this);

                // check if GPS enabled
                if(GPSTracker.canGetLocation()){

                    double latitude = GPSTracker.getLatitude();
                    double longitude = GPSTracker.getLongitude();

                    // \n is for new line
                    Location.setText("latitude : " + latitude + "-----" +"longitude : "+longitude );
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    GPSTracker.showSettingsAlert();
                }
            }
        });

    }

}
