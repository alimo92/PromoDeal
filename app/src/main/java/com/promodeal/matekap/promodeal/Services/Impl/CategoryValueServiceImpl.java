package com.promodeal.matekap.promodeal.Services.Impl;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Entities.CategoryValue;
import com.promodeal.matekap.promodeal.Services.CategoryValueService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali on 25/05/2016.
 */
public class CategoryValueServiceImpl extends BasicServiceImpl<CategoryValue> implements CategoryValueService {
    @Override
    public JSONObject getJSON(CategoryValue model) throws JSONException {
        return null;
    }

    @Override
    public CategoryValue getModel(JSONObject object) throws JSONException {
        CategoryValue model = new CategoryValue();
        model.setIdCategoryValue(object.getInt("IdCategoryValue"));
        model.setNameCategoryValue(object.getString("CategoryValue"));
        return model;
    }
}
