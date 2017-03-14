package com.promodeal.matekap.promodeal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Message;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Ali on 11/05/2016.
 */
public class MessageAdapter extends BaseAdapter {
    Context context;
    List<Message> listmessage;
    private LayoutInflater mInflater;

    public MessageAdapter(Activity activity,List<Message> m){
        listmessage=m;
        context=activity;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return listmessage.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = mInflater.inflate(R.layout.item_message, null);
        holder.tv=(TextView) rowView.findViewById(R.id.content_message);
        holder.tv.setText(listmessage.get(position).getContentMessage());
        try {
            if(listmessage.get(position).getUser().getIdUser()== AppController.getCurrentUser((Activity) context).getIdUser()){
                holder.tv.setGravity(Gravity.RIGHT);
                rowView.setPaddingRelative(0, 0, 40, 0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rowView;
    }

    public class Holder
    {
        TextView tv;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }


}
