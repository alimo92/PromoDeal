package com.promodeal.matekap.promodeal.Core.Service;

import org.json.JSONException;

/**
 * Created by Ali on 14/04/2016.
 */
public interface ServiceCallback<M> {
    void OnSuccess(M Entity) throws JSONException;
}
