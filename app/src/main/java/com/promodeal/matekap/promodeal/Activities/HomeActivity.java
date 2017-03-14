package com.promodeal.matekap.promodeal.Activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Fragments.Fragment_Home_1;
import com.promodeal.matekap.promodeal.Fragments.Fragment_Home_2;
import com.promodeal.matekap.promodeal.Fragments.Fragment_Home_3;
import com.promodeal.matekap.promodeal.Fragments.Fragment_Home_4;
import com.promodeal.matekap.promodeal.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends FragmentActivity implements
        Fragment_Home_1.OnFragmentInteractionListener,Fragment_Home_2.OnFragmentInteractionListener,Fragment_Home_3.OnFragmentInteractionListener,
        Fragment_Home_4.OnFragmentInteractionListener,FragmentManager.OnBackStackChangedListener
{

    Button Disconnect;
    SharedPreferences mPrefs;
    SharedPreferences.Editor Edit;

    TextView results;

    RadioGroup radiogroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;

    private Fragment_Home_1 fragment_home_1;
    private Fragment_Home_2 fragment_home_2;
    private Fragment_Home_3 fragment_home_3;
    private Fragment_Home_4 fragment_home_4;

    FragmentTransaction transaction;
    FragmentTransaction firsttransaction;

    List<Integer> list;
    int size;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        fragment_home_1 = new Fragment_Home_1();
        fragment_home_2 = new Fragment_Home_2();
        fragment_home_3 = new Fragment_Home_3();
        fragment_home_4 = new Fragment_Home_4();


        radioButton1 =(RadioButton) findViewById(R.id.RadioButtonMain1);
        radioButton2 =(RadioButton) findViewById(R.id.RadioButtonMain2);
        radioButton3 =(RadioButton) findViewById(R.id.RadioButtonMain3);
        radioButton4 =(RadioButton) findViewById(R.id.RadioButtonMain4);
        radiogroup =(RadioGroup) findViewById(R.id.RadioGroupMain);
        results= (TextView) findViewById(R.id.testehome);

        list = new ArrayList<>();

        mPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        Edit=mPrefs.edit();
        Disconnect =(Button)findViewById(R.id.Disconnect);

        firsttransaction = getSupportFragmentManager().beginTransaction();
        firsttransaction.add(R.id.fragment_container, fragment_home_1);
        firsttransaction.addToBackStack(null);
        firsttransaction.commit();

        Disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit.remove("CurrentUser");
                Edit.commit();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        results.setText(radiogroup.getCheckedRadioButtonId() + "->first id");
        list.add(radiogroup.getCheckedRadioButtonId());

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId){

                    case R.id.RadioButtonMain1:
                        list.clear();
                        list.add(R.id.RadioButtonMain1);
                        transaction.replace(R.id.fragment_container, fragment_home_1);
                        transaction.addToBackStack("fragment_home_1");
                        results.setText(list.size() + "->SIZE");
                        transaction.commit();
                        break;

                    case R.id.RadioButtonMain2:
                        list.add(R.id.RadioButtonMain2);
                        transaction.replace(R.id.fragment_container, fragment_home_2);
                        transaction.addToBackStack("fragment_home_2");
                        results.setText(list.size() + "->SIZE");
                        transaction.commit();
                        break;

                    case R.id.RadioButtonMain3:
                        list.add(R.id.RadioButtonMain3);
                        transaction.replace(R.id.fragment_container, fragment_home_3);
                        transaction.addToBackStack("fragment_home_3");
                        results.setText(list.size() + "->SIZE");
                        transaction.commit();
                        break;

                    case R.id.RadioButtonMain4:
                        list.add(R.id.RadioButtonMain4);
                        transaction.replace(R.id.fragment_container, fragment_home_4);
                        transaction.addToBackStack("fragment_home_4");
                        results.setText(list.size() + "->SIZE");
                        transaction.commit();
                        break;
                }
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        list.remove(list.size()-1);
        if(list.size()==0){
            finish();
        }else{
            RadioButton temp = (RadioButton)findViewById(list.get(list.size()-1));
            temp.setChecked(true);
            results.setText(list.get(list.size()-1)+"->id "+ list.size());
        }
        results.setText(list.size()+"->SIZE");

    }

    @Override
    public void onBackStackChanged() {

    }
}
