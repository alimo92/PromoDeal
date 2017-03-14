package com.promodeal.matekap.promodeal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.promodeal.matekap.promodeal.Controllers.PostController;
import com.promodeal.matekap.promodeal.Core.Volley.AppController;
import com.promodeal.matekap.promodeal.R;

import org.json.JSONException;
import org.w3c.dom.Text;

public class PostDetailActivity extends AppCompatActivity {

    PostController postController;
    EditText DetailPost_WriteComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detail);

        DetailPost_WriteComment =(EditText) findViewById(R.id.detailpost_writecomment_show);

        postController = new PostController(this);

        postController.SetDetailPost();

        final Button Send = (Button) findViewById(R.id.detailpost_sendcomment_show);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DetailPost_WriteComment.getText().toString().equals("")){

                }else{
                    try {
                        postController.WriteComment();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
