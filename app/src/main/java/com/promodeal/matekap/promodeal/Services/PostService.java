package com.promodeal.matekap.promodeal.Services;


import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Post;

import org.json.JSONException;

/**
 * Created by Ali on 01/06/2016.
 */
public interface PostService {
    void AddPost(Post model, final ServiceCallback<Post> serviceCallback) throws JSONException;
    void GetAllPosts(final ServiceCallbackList<Post> serviceCallbackList) throws JSONException;
}
