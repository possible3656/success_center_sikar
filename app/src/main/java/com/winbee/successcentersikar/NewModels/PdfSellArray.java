package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PdfSellArray implements Serializable {
    @SerializedName("ContentId")
    @Expose
    private String contentId;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("Thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Topic")
    @Expose
    private String topic;
    @SerializedName("Faculty")
    @Expose
    private String faculty;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("DisplayAmount")
    @Expose
    private String displayAmount;
    @SerializedName("SellingAmount")
    @Expose
    private String sellingAmount;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("IsPaid")
    @Expose
    private Integer isPaid;
    @SerializedName("CoverImage")
    @Expose
    private String coverImage;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDisplayAmount() {
        return displayAmount;
    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    public String getSellingAmount() {
        return sellingAmount;
    }

    public void setSellingAmount(String sellingAmount) {
        this.sellingAmount = sellingAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

}