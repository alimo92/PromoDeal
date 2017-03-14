package com.promodeal.matekap.promodeal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.Fragments.HomeFragment;
import com.promodeal.matekap.promodeal.Fragments.ProfilFragment;
import com.promodeal.matekap.promodeal.Fragments.SearchFragment;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ProfilFragment profilFragment;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private FragmentTransaction firsttransaction;

    private User CurrentUser;

    TextView Name;
    TextView Email;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profilFragment = new ProfilFragment();
        searchFragment = new SearchFragment();
        homeFragment = new HomeFragment();

        firsttransaction = getSupportFragmentManager().beginTransaction();
        firsttransaction.add(R.id.framelayout_content, homeFragment);
        firsttransaction.addToBackStack(null);
        firsttransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */
                Intent i = new Intent(MainActivity.this,AddPostActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            CurrentUser = AppController.getCurrentUser(this);
            setProfilInformation(CurrentUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String var =""+id;
        firsttransaction = getSupportFragmentManager().beginTransaction();
        if(id==R.id.nav_home){
            firsttransaction.replace(R.id.framelayout_content,homeFragment);
            firsttransaction.addToBackStack(""+id);
            firsttransaction.commit();
        } else if (id == R.id.nav_profil) {
            firsttransaction.replace(R.id.framelayout_content,profilFragment);
            firsttransaction.addToBackStack(""+id);
            firsttransaction.commit();
        } else if (id == R.id.nav_search) {
            firsttransaction.replace(R.id.framelayout_content,searchFragment);
            firsttransaction.addToBackStack(""+id);
            firsttransaction.commit();
        } else if (id == R.id.nav_inbox) {
            Intent i = new Intent(MainActivity.this,ConversationActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_setting) {

        }else if (id == R.id.nav_logout) {
            AppController.RemoveCurrentUser(this);
            Intent i = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setProfilInformation(User user){
        View header = navigationView.getHeaderView(0);

        TextView Email = (TextView) header.findViewById(R.id.profiluser_email);
        TextView Name = (TextView) header.findViewById(R.id.profiluser_name);
        final CircleImageView Image =(CircleImageView) header.findViewById(R.id.profiluser_picture);

        Email.setText(user.getEmailUser());
        Name.setText(user.getFirstNameUser()+"  "+ user.getLastNameUser());

        Log.e("URL",user.getImageUser());
        AppController.getInstance().getImageLoader().get(user.getImageUser(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if(response!=null){
                    Image.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }
}
