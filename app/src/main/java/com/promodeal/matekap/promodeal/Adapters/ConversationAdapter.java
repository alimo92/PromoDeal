package com.promodeal.matekap.promodeal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Activities.MessageActivity;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Conversation;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.ConversationServiceImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ali on 11/05/2016.
 */
public class ConversationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<Conversation> conversations;
    Context context;
    ConversationServiceImpl C;

    public ConversationAdapter(Activity activity,List<Conversation> c){
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conversations=c;
        context =activity;
        C= new ConversationServiceImpl();
    }
    @Override
    public int getCount() {
        return conversations.size();
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = mInflater.inflate(R.layout.item_conversation, null);
        holder.tv=(TextView) rowView.findViewById(R.id.conversation_name);

        try {
            holder.tv.setText(CreateNameConversation(conversations.get(position)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject object = C.getJSON(conversations.get(position));
                    Intent i = new Intent(context, MessageActivity.class);
                    i.putExtra("Conversation",object.toString());
                    context.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return rowView;
    }

    public class Holder
    {
        TextView tv;
    }

    String CreateNameConversation(Conversation conversation) throws JSONException {
        String name ="";
        for(int i=0;i<conversation.getUsers().size();i++){
            if(conversation.getUsers().get(i).getEmailUser().equals(AppController.getCurrentUser((Activity) context).getEmailUser())==false){
                User tempuser = conversation.getUsers().get(i);
                name+= tempuser.getFirstNameUser() +" "+tempuser.getLastNameUser();

                if(i<conversation.getUsers().size()-2){
                    name+= " , ";
                }

            }

        }
        return name;
    }
}
