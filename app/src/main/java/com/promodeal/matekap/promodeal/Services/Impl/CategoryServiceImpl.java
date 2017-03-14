package com.promodeal.matekap.promodeal.Services.Impl;

import android.util.Log;

import com.promodeal.matekap.promodeal.Core.Service.BasicServiceImpl;
import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Core.URL.Impl.URLBuilderImpl;
import com.promodeal.matekap.promodeal.Core.Volley.ArrayCallback;
import com.promodeal.matekap.promodeal.Entities.Category;
import com.promodeal.matekap.promodeal.Entities.CategoryValue;
import com.promodeal.matekap.promodeal.Services.CategoryService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 25/05/2016.
 */
public class CategoryServiceImpl extends BasicServiceImpl<Category> implements CategoryService {

    private CategoryValueServiceImpl categoryValueService;

    public CategoryServiceImpl(){
        categoryValueService  = new CategoryValueServiceImpl();
        urlBuilderImpl = new URLBuilderImpl();
    }

    @Override
    public JSONObject getJSON(Category model) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("NameCategory",model.getNameCategory());
        return object;
    }

    @Override
    public Category getModel(JSONObject object) throws JSONException {
        Category model = new Category();
        JSONArray array = object.getJSONArray("CategoryValues");
        List<CategoryValue> list = new ArrayList<>();

        for(int i=0;i<array.length();i++){
            CategoryValue categoryValue = categoryValueService.getModel(array.getJSONObject(i));
            list.add(categoryValue);
        }

        model.setIdCategory(object.getInt("IdCategory"));
        model.setNameCategory(object.getString("NameCategory"));
        model.setCategoryValues(list);

        return model;
    }


    @Override
    public void getCategories(final ServiceCallbackList<Category> serviceCallbackList) {
        //String URL = "http://192.168.23.1:8080/category/";
        String URL = urlBuilderImpl.getURL("category","");
        GetArrayResponse(URL, new ArrayCallback() {
            @Override
            public void OnSuccess(JSONArray Response) throws JSONException {
                List<Category> list = new ArrayList<Category>();
                for(int i=1;i<Response.length();i++){
                    list.add(getModel(Response.getJSONObject(i)));
                }
                serviceCallbackList.OnSuccess(list);
            }
        });
    }
}
