package com.promodeal.matekap.promodeal.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Activities.MainActivity;
import com.promodeal.matekap.promodeal.Controllers.PostController;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    PostController postController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        ListView listview =(ListView) view.findViewById(R.id.list_post);

        postController = new PostController(getActivity(),listview);

        try {
            postController.GetAllPosts();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            postController.GetAllPosts();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
