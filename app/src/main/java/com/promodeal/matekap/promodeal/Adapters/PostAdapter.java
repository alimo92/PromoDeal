package com.promodeal.matekap.promodeal.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.promodeal.matekap.promodeal.Activities.MainActivity;
import com.promodeal.matekap.promodeal.Activities.PostDetailActivity;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.Post;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;

import java.util.List;

/**
 * Created by Ali on 02/06/2016.
 */
public class PostAdapter extends BaseAdapter {

    Activity activity;
    List<Post> postList;
    LayoutInflater inflater;

    public PostAdapter(Activity a,List<Post> l){
        activity=a;
        postList =l;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return postList.size();
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


        View rowView;
        rowView = inflater.inflate(R.layout.item_post, null);

        TextView Title=(TextView) rowView.findViewById(R.id.title_post_show);
        TextView user=(TextView) rowView.findViewById(R.id.user_post_show);
        TextView Description=(TextView) rowView.findViewById(R.id.description_post_show);
        TextView CommentSize =(TextView) rowView.findViewById(R.id.commentsize_post_show);
        TextView AverageRating =(TextView) rowView.findViewById(R.id.averagerating_post_show);
        TextView DateCreated =(TextView) rowView.findViewById(R.id.date_post_show);
        final ImageView ImagePost =(ImageView) rowView.findViewById(R.id.image_post_show);

        final Post tempPost = postList.get(position);

        Title.setText(tempPost.getTitlePost());
        Description.setText(tempPost.getDescriptionPost());
        CommentSize.setText(tempPost.getComments().size()+"  Comments");
        AverageRating.setText("Average Rating  " +tempPost.getAverageRating() +"   "+"("+ tempPost.getRatings().size()+")");
        DateCreated.setText(tempPost.getDateCreated().toString());

        User current = tempPost.getUser();
        user.setText(current.getFirstNameUser() + " " +current.getLastNameUser());

        String URL = postList.get(position).getImagePost();
        //Log.e("URL Image",URL);
        AppController.getInstance().getImageLoader().get(URL, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //Log.e("RESPONSE",response.toString());
                if(response!=null){
                    ImagePost.setImageBitmap(response.getBitmap());
                    tempPost.setBitmapPicture(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.getInstance().setSelectedPost(tempPost);
                Intent i = new Intent(activity, PostDetailActivity.class);
                activity.startActivity(i);
            }
        });

        return rowView;
    }
}
