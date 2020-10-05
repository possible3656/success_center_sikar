package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PdfContentArray implements Serializable {
    @SerializedName("Topic")
    @Expose
    private String topic;
    @SerializedName("Faculty")
    @Expose
    private String faculty;
    @SerializedName("DocumentId")
    @Expose
    private String documentId;
    @SerializedName("AccessType")
    @Expose
    private String accessType;
    @SerializedName("Storage")
    @Expose
    private String storage;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("URL")
    @Expose
    private String uRL;

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

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

}
