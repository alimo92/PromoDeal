package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.promodeal.matekap.promodeal.Adapters.PostAdapter;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.Entities.CategoryValue;
import com.promodeal.matekap.promodeal.Entities.Comment;
import com.promodeal.matekap.promodeal.Entities.LocationPost;
import com.promodeal.matekap.promodeal.Entities.Post;
import com.promodeal.matekap.promodeal.Entities.User;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.CommentServiceImpl;
import com.promodeal.matekap.promodeal.Services.Impl.PostServiceImpl;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ali on 01/06/2016.
 */
public class PostController {

    EditText TitlePost;
    EditText DescriptionPost;
    LocationPost locationPost;
    List<Integer> listIds;
    String ImageUrl;
    Activity Activity;

    PostServiceImpl postService;
    CommentServiceImpl commentService;

    ListView listView;

    TextView DetailPost_User, DetailPost_AverageRating,DetailPost_DateCreated,DetailPost_Title,DetailPost_Description;
    ImageView DetailPost_Picture;
    EditText DetailPost_WriteComment;
    CircleImageView DetailPost_UserImage;
    RatingBar DetailPost_RatingBar;

    public PostController(Activity activity, LocationPost location,List<Integer> list,String url){


        TitlePost =(EditText) activity.findViewById(R.id.title_post);
        DescriptionPost =(EditText) activity.findViewById(R.id.description_post);


        locationPost =location;
        listIds=list;
        ImageUrl = url;
        Activity =activity;

        postService = new PostServiceImpl();
    }

    public PostController(Activity activity,ListView v){
        Activity =activity;
        postService = new PostServiceImpl();
        listView=v;
    }

    public PostController(Activity activity){

        DetailPost_AverageRating =(TextView) activity.findViewById(R.id.detailpost_averagerating_show);
        DetailPost_User =(TextView) activity.findViewById(R.id.detailpost_user_show);
        DetailPost_Picture =(ImageView) activity.findViewById(R.id.detailpost_image_show);
        DetailPost_WriteComment =(EditText) activity.findViewById(R.id.detailpost_writecomment_show);
        DetailPost_UserImage =(CircleImageView) activity.findViewById(R.id.detailpost_userimage_show);
        DetailPost_DateCreated =(TextView) activity.findViewById(R.id.detailpost_datecreated_show);
        DetailPost_Title =(TextView) activity.findViewById(R.id.detailpost_title_show);
        DetailPost_Description =(TextView) activity.findViewById(R.id.detailpost_description_show);
        DetailPost_RatingBar =(RatingBar)activity.findViewById(R.id.detailpost_ratingbar_show);

        Activity =activity;
        postService = new PostServiceImpl();
        commentService = new CommentServiceImpl();
    }


    public void AddPost(Post model) throws JSONException {
        postService.AddPost(model, new ServiceCallback<Post>() {
            @Override
            public void OnSuccess(Post Entity) throws JSONException {
                Activity.finish();
            }
        });
    }

    public Post CreatePost() throws JSONException {
        Post model = new Post();
        List<CategoryValue> categoryValueList = new ArrayList<>();
        User current = AppController.getCurrentUser(Activity);


        model.setTitlePost(TitlePost.getText().toString());
        model.setDescriptionPost(DescriptionPost.getText().toString());
        model.setLocationPost(locationPost);
        model.setUser(current);
        model.setImagePost(ImageUrl);

        for(int i=0;i<listIds.size();i++){
            CategoryValue temp = new CategoryValue();
            temp.setIdCategoryValue(listIds.get(i));
            categoryValueList.add(temp);
        }

        model.setCategoryValueList(categoryValueList);

        return model;
    }

    public void GetAllPosts() throws JSONException {
        postService.GetAllPosts(new ServiceCallbackList<Post>() {
            @Override
            public void OnSuccess(List<Post> list) {
                PostAdapter postAdapter = new PostAdapter(Activity,list);
                listView.setAdapter(postAdapter);
            }
        });
    }

    public void SetDetailPost() {

        Post CurrentPost =AppController.getInstance().getSelectedPost();
        User UserPost = CurrentPost.getUser();

        DetailPost_User.setText(UserPost.getFirstNameUser()+" "+UserPost.getLastNameUser());
        DetailPost_Picture.setImageBitmap(CurrentPost.getBitmapPicture());
        DetailPost_AverageRating.setText("Average Rating :"+CurrentPost.getAverageRating()+"  ("+CurrentPost.getRatings().size()+")");
        DetailPost_DateCreated.setText(CurrentPost.getDateCreated().toString());
        DetailPost_Description.setText(CurrentPost.getDescriptionPost());
        DetailPost_Title.setText(CurrentPost.getTitlePost());
        DetailPost_RatingBar.setRating((float) CurrentPost.getAverageRating());

        AppController.getInstance().getImageLoader().get(UserPost.getImageUser(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                DetailPost_UserImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public void WriteComment() throws JSONException {
        Comment model = CreateCommentToSend();
        DetailPost_WriteComment.setText("");
        commentService.SendComment(model, new ServiceCallback<Comment>() {
            @Override
            public void OnSuccess(Comment Entity) throws JSONException {

            }
        });


    }

    public PostServiceImpl getservice(){
        return  this.postService;
    }

    Comment CreateCommentToSend() throws JSONException {
        Comment model = new Comment();

        User CurrentUser = AppController.getCurrentUser(Activity);
        Post SelectedPost = AppController.getInstance().getSelectedPost();

        model.setIdPost(SelectedPost.getIdPost());
        model.setUser(CurrentUser);
        model.setContentComment(DetailPost_WriteComment.getText().toString());

        return model;
    }
}
