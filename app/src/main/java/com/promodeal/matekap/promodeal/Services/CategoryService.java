package com.promodeal.matekap.promodeal.Services;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Category;

/**
 * Created by Ali on 25/05/2016.
 */
public interface CategoryService {
    void getCategories(ServiceCallbackList<Category> serviceCallbackList);
}
