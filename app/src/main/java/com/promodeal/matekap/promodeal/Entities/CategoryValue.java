package com.promodeal.matekap.promodeal.Entities;

/**
 * Created by Ali on 25/05/2016.
 */
public class CategoryValue {
    private int IdCategoryValue;
    private String NameCategoryValue;

    public CategoryValue() {
    }

    public CategoryValue(int idCategoryValue, String nameCategoryValue) {
        IdCategoryValue = idCategoryValue;
        NameCategoryValue = nameCategoryValue;
    }

    public int getIdCategoryValue() {
        return IdCategoryValue;
    }

    public void setIdCategoryValue(int idCategoryValue) {
        IdCategoryValue = idCategoryValue;
    }

    public String getNameCategoryValue() {
        return NameCategoryValue;
    }

    public void setNameCategoryValue(String nameCategoryValue) {
        NameCategoryValue = nameCategoryValue;
    }
}
