package com.promodeal.matekap.promodeal.Core.Service;

import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 14/04/2016.
 */
public interface BasicService<M>{
    void GetArrayResponse(String URL,JSONObject object,ArrayCallback arrayCallback);
    void GetArrayResponse(String URL,ArrayCallback arrayCallback);
    JSONObject getJSON(M model) throws JSONException;
    M getModel(JSONObject object) throws JSONException;

}
