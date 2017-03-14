package com.promodeal.matekap.promodeal.Controllers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.promodeal.matekap.promodeal.Core.Service.ServiceCallbackList;
import com.promodeal.matekap.promodeal.Entities.Category;
import com.promodeal.matekap.promodeal.Entities.CategoryValue;
import com.promodeal.matekap.promodeal.R;
import com.promodeal.matekap.promodeal.Services.Impl.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 25/05/2016.
 */
public class CategoryController  implements AdapterView.OnItemSelectedListener{

    private Spinner CategorySpinner;
    private Spinner CategoryValueSpinner;

    private Button AddCategoryValue;

    private CategoryServiceImpl categoryService;
    private Activity activity;

    private List<Category> tempList;
    private List<Integer> listIds;

    ArrayAdapter<String> CategoryAdapter;
    ArrayAdapter<String> CategoryValueAdapter;

    public CategoryController(Activity activity){
        this.activity =activity;
        categoryService = new CategoryServiceImpl();
        tempList = new ArrayList<>();
        listIds = new ArrayList<>();

        CategorySpinner =(Spinner) activity.findViewById(R.id.spinner_category);
        CategoryValueSpinner=(Spinner) activity.findViewById(R.id.spinner_categoryvalue);
        CategorySpinner.setOnItemSelectedListener(this);
        CategoryValueSpinner.setOnItemSelectedListener(this);

        AddCategoryValue =(Button) activity.findViewById(R.id.add_categoryvalue);
    }

    public void getCategories(){
        categoryService.getCategories(new ServiceCallbackList<Category>() {
            @Override
            public void OnSuccess(List<Category> list) {
                List<String> categories = getStringCategories(list);
                CategoryAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,categories);
                CategorySpinner.setAdapter(CategoryAdapter);
                tempList =list;
            }
        });
    }

    public List<String> getStringCategories(List<Category> list){
        List<String> l = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            l.add(list.get(i).getNameCategory());
        }
        return l;
    }

    public List<String> getStringCategoryValues(List<CategoryValue> list){
        List<String> l = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            l.add(list.get(i).getNameCategoryValue());
        }
        return l;
    }

    public List<CategoryValue> getCategoryValues(List<Category> list, String item){
        int position=0 ;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNameCategory().equals(item)){
                position =i;
                break;
            }
        }
        return list.get(position).getCategoryValues();
    }

    public int getIdCategoryValue(List<CategoryValue> list,String item){
        int position =0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getNameCategoryValue().equals(item)){
                position =i;
                break;
            }
        }
        CategoryValue categoryValue =list.get(position);
        return categoryValue.getIdCategoryValue();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner tempSpinner =(Spinner) parent;

        switch(tempSpinner.getId()){
            case R.id.spinner_category:
                String item = parent.getItemAtPosition(position).toString();
                List<CategoryValue> listcategoryvalue = getCategoryValues(tempList,item);
                CategoryValueAdapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,getStringCategoryValues(listcategoryvalue));
                CategoryValueSpinner.setAdapter(CategoryValueAdapter);
                AddCategoryValue.setOnClickListener(new AddCategoryValueListener(listcategoryvalue));
                break;

            case R.id.spinner_categoryvalue:

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class AddCategoryValueListener implements View.OnClickListener{
        private List<CategoryValue> categoryValueList;

        public AddCategoryValueListener(List<CategoryValue> list){
            categoryValueList = list;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id ==R.id.add_categoryvalue && CategoryValueAdapter.getCount()!=0){

                String itemcategoryvalue =CategoryValueSpinner.getSelectedItem().toString();
                String itemcategory = CategorySpinner.getSelectedItem().toString();

                listIds.add(getIdCategoryValue(categoryValueList,itemcategoryvalue));
//                Toast.makeText(activity, ""+listIds.size(), Toast.LENGTH_LONG).show();

                CategoryAdapter.remove(itemcategory);
                CategoryAdapter.notifyDataSetChanged();

                if(CategoryAdapter.getCount()!=0){
                    itemcategory = CategorySpinner.getSelectedItem().toString();

                    List<CategoryValue> listcategoryvalue = getCategoryValues(tempList,itemcategory);
                    CategoryValueAdapter = new ArrayAdapter<String>(activity,R.layout.support_simple_spinner_dropdown_item,getStringCategoryValues(listcategoryvalue));
                    CategoryValueSpinner.setAdapter(CategoryValueAdapter);
                }else{
                    CategoryValueAdapter.clear();
                    CategoryValueAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public List<Integer> getListId(){
        return this.listIds;
    }
}
