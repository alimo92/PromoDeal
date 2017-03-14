package com.promodeal.matekap.promodeal.Services;


import com.promodeal.matekap.promodeal.Core.Service.ServiceCallback;
import com.promodeal.matekap.promodeal.Entities.LocationPost;

import org.json.JSONException;

/**
 * Created by Ali on 02/05/2016.
 */
public interface LocationPostService {
    void CreateLocation(LocationPost model,ServiceCallback<LocationPost> serviceCallback ) throws JSONException;
    LocationPost getLocation();
}
