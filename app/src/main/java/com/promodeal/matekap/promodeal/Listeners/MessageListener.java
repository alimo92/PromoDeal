package com.promodeal.matekap.promodeal.Listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.promodeal.matekap.promodeal.Controllers.MessageController;
import com.promodeal.matekap.promodeal.R;

/**
 * Created by Ali on 12/05/2016.
 */
public class MessageListener implements View.OnClickListener {

    Context context;
    MessageController messageController;
    public MessageListener(Activity activity){
        context=activity;
        messageController = new MessageController(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_message:

                break;
        }
    }
}
