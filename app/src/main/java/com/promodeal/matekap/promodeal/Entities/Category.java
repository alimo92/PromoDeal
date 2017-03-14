package com.promodeal.matekap.promodeal.Entities;

import java.util.List;

/**
 * Created by Ali on 25/05/2016.
 */
public class Category {

    private int IdCategory;
    private String NameCategory;
    private List<CategoryValue> CategoryValues;

    public Category(int idCategory, String nameCategory, List<CategoryValue> categoryValues) {
        IdCategory = idCategory;
        NameCategory = nameCategory;
        CategoryValues = categoryValues;
    }

    public Category() {
    }

    public int getIdCategory() {
        return IdCategory;
    }

    public void setIdCategory(int idCategory) {
        IdCategory = idCategory;
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    public List<CategoryValue> getCategoryValues() {
        return CategoryValues;
    }

    public void setCategoryValues(List<CategoryValue> categoryValues) {
        CategoryValues = categoryValues;
    }
}
