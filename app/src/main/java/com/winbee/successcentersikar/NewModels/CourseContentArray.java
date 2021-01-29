package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CourseContentArray implements Serializable {
    @SerializedName("Bucket_ID")
    @Expose
    private String bucket_ID;
    @SerializedName("Bucket_Name")
    @Expose
    private String bucket_Name;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("display_price")
    @Expose
    private Integer display_price;
    @SerializedName("discount_price")
    @Expose
    private Integer discount_price;
    @SerializedName("total_discount")
    @Expose
    private Integer total_discount;
    @SerializedName("Total_Video")
    @Expose
    private Integer total_Video;
    @SerializedName("Total_Document")
    @Expose
    private Integer total_Document;
    @SerializedName("Child_Link")
    @Expose
    private String child_Link;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("TotalPoints")
    @Expose
    private Integer totalPoints;
    @SerializedName("DP_AF_RC")
    @Expose
    private Integer dP_AF_RC;
    @SerializedName("release_status")
    @Expose
    private Integer release_status;
    @SerializedName("course_show")
    @Expose
    private Integer course_show;
    @SerializedName("Bucket_Image")
    @Expose
    private String bucket_Image;
    @SerializedName("Bucket_Cover_Image")
    @Expose
    private String bucket_Cover_Image;
    @SerializedName("Paid")
    @Expose
    private Integer paid;
    @SerializedName("ExamSectionId")
    @Expose
    private String examSectionId;
    @SerializedName("TotalPapers")
    @Expose
    private String totalPapers;

    public String getBucket_ID() {
        return bucket_ID;
    }

    public void setBucket_ID(String bucket_ID) {
        this.bucket_ID = bucket_ID;
    }

    public String getBucket_Name() {
        return bucket_Name;
    }

    public void setBucket_Name(String bucket_Name) {
        this.bucket_Name = bucket_Name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getDisplay_price() {
        return display_price;
    }

    public void setDisplay_price(Integer display_price) {
        this.display_price = display_price;
    }

    public Integer getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(Integer discount_price) {
        this.discount_price = discount_price;
    }

    public Integer getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(Integer total_discount) {
        this.total_discount = total_discount;
    }

    public Integer getTotal_Video() {
        return total_Video;
    }

    public void setTotal_Video(Integer total_Video) {
        this.total_Video = total_Video;
    }

    public Integer getTotal_Document() {
        return total_Document;
    }

    public void setTotal_Document(Integer total_Document) {
        this.total_Document = total_Document;
    }

    public String getChild_Link() {
        return child_Link;
    }

    public void setChild_Link(String child_Link) {
        this.child_Link = child_Link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getDP_AF_RC() {
        return dP_AF_RC;
    }

    public void setDP_AF_RC(Integer dP_AF_RC) {
        this.dP_AF_RC = dP_AF_RC;
    }

    public Integer getRelease_status() {
        return release_status;
    }

    public void setRelease_status(Integer release_status) {
        this.release_status = release_status;
    }

    public Integer getCourse_show() {
        return course_show;
    }

    public void setCourse_show(Integer course_show) {
        this.course_show = course_show;
    }

    public String getBucket_Image() {
        return bucket_Image;
    }

    public void setBucket_Image(String bucket_Image) {
        this.bucket_Image = bucket_Image;
    }

    public String getBucket_Cover_Image() {
        return bucket_Cover_Image;
    }

    public void setBucket_Cover_Image(String bucket_Cover_Image) {
        this.bucket_Cover_Image = bucket_Cover_Image;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getExamSectionId() {
        return examSectionId;
    }

    public void setExamSectionId(String examSectionId) {
        this.examSectionId = examSectionId;
    }

    public String getTotalPapers() {
        return totalPapers;
    }

    public void setTotalPapers(String totalPapers) {
        this.totalPapers = totalPapers;
    }

}