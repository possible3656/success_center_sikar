package com.winbee.successcentersikar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FacultyName  implements Serializable {
    @SerializedName("FilterType")
    @Expose
    private String filterType;
    @SerializedName("CategoryData")
    @Expose
    private List<CategoryDatum> categoryData = null;

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }

}