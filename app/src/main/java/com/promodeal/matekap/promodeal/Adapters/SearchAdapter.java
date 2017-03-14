package com.promodeal.matekap.promodeal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Activities.MessageActivity;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.UserServiceImpl;

import org.json.JSONException;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ali on 13/05/2016.
 */
public class SearchAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    Context context;
    List<User> listusers;
    UserServiceImpl userService;

    public SearchAdapter(Activity activity,List<User> users){
        listusers=users;
        context=activity;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userService = new UserServiceImpl();
    }

    @Override
    public int getCount() {
        return listusers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = mInflater.inflate(R.layout.item_search, null);
        holder.tv=(TextView) rowView.findViewById(R.id.user_result);
        holder.tv.setText(listusers.get(position).getFirstNameUser()+"  "+listusers.get(position).getLastNameUser());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, MessageActivity.class);
                try {
                    i.putExtra("Receiver", userService.getJSON(listusers.get(position)).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.startActivity(i);

            }
        });
        return rowView;
    }


    public class Holder
    {
        TextView tv;
    }
}


