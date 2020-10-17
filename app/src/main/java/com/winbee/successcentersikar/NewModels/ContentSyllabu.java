package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContentSyllabu implements Serializable {

    @SerializedName("Course_Item_Name")
    @Expose
    private String course_Item_Name;
    @SerializedName("Course_Item_Video")
    @Expose
    private Integer course_Item_Video;
    @SerializedName("Course_Item_Notes")
    @Expose
    private Integer course_Item_Notes;

    public String getCourse_Item_Name() {
        return course_Item_Name;
    }

    public void setCourse_Item_Name(String course_Item_Name) {
        this.course_Item_Name = course_Item_Name;
    }

    public Integer getCourse_Item_Video() {
        return course_Item_Video;
    }

    public void setCourse_Item_Video(Integer course_Item_Video) {
        this.course_Item_Video = course_Item_Video;
    }

    public Integer getCourse_Item_Notes() {
        return course_Item_Notes;
    }

    public void setCourse_Item_Notes(Integer course_Item_Notes) {
        this.course_Item_Notes = course_Item_Notes;
    }

}