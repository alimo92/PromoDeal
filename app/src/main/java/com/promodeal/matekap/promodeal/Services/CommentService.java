package com.promodeal.matekap.promodeal.Services;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Entities.Comment;

import org.json.JSONException;

/**
 * Created by Ali on 02/06/2016.
 */
public interface CommentService {
    void SendComment(Comment model, final ServiceCallback<Comment> serviceCallback) throws JSONException;
}
