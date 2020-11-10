package com.winbee.successcentersikar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SIACDetailsDataModel {
    @SerializedName("BucketID")
    @Expose
    private String bucketID;
    @SerializedName("PaperID")
    @Expose
    private String paperID;
    @SerializedName("PaperName")
    @Expose
    private String paperName;
    @SerializedName("PaperSection_Encode")
    @Expose
    private String paperSection_Encode;
    @SerializedName("IsNegativeMarking_encode")
    @Expose
    private String isNegativeMarking_encode;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("IsOpen")
    @Expose
    private String isOpen;
    @SerializedName("OpenDate")
    @Expose
    private String openDate;
    @SerializedName("IsAttempted")
    @Expose
    private Boolean isAttempted;
    @SerializedName("Is_Live")
    @Expose
    private Boolean is_Live;
    @SerializedName("Total_Number_of_question")
    @Expose
    private Integer total_Number_of_question;
    @SerializedName("Is_closed_Notification_On")
    @Expose
    private Boolean is_closed_Notification_On;
    @SerializedName("Test_Closed_date")
    @Expose
    private String test_Closed_date;
    @SerializedName("Test_Closed_Message")
    @Expose
    private String test_Closed_Message;
    @SerializedName("Is_Test_category_Specail")
    @Expose
    private Boolean is_Test_category_Specail;
    @SerializedName("Is_ReAttempt")
    @Expose
    private Boolean is_ReAttempt;
    @SerializedName("ReAttempt_Error_Message")
    @Expose
    private String reAttempt_Error_Message;
    @SerializedName("Is_Instant_View_Rank")
    @Expose
    private Boolean is_Instant_View_Rank;
    @SerializedName("Is_Instant_View_Result")
    @Expose
    private Boolean is_Instant_View_Result;
    @SerializedName("Is_Notification")
    @Expose
    private Boolean is_Notification;
    @SerializedName("Notification_Message")
    @Expose
    private String notification_Message;
    @SerializedName("IsNegativeMarking_decode")
    @Expose
    private String isNegativeMarking_decode;
    @SerializedName("IsPremium_encode")
    @Expose
    private String isPremium_encode;
    @SerializedName("IsPremium_decode")
    @Expose
    private String isPremium_decode;
    @SerializedName("Description")
    @Expose
    private String description;

    public String getBucketID() {
        return bucketID;
    }

    public void setBucketID(String bucketID) {
        this.bucketID = bucketID;
    }

    public String getPaperID() {
        return paperID;
    }

    public void setPaperID(String paperID) {
        this.paperID = paperID;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getPaperSection_Encode() {
        return paperSection_Encode;
    }

    public void setPaperSection_Encode(String paperSection_Encode) {
        this.paperSection_Encode = paperSection_Encode;
    }

    public String getIsNegativeMarking_encode() {
        return isNegativeMarking_encode;
    }

    public void setIsNegativeMarking_encode(String isNegativeMarking_encode) {
        this.isNegativeMarking_encode = isNegativeMarking_encode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public Boolean getIsAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(Boolean isAttempted) {
        this.isAttempted = isAttempted;
    }

    public Boolean getIs_Live() {
        return is_Live;
    }

    public void setIs_Live(Boolean is_Live) {
        this.is_Live = is_Live;
    }

    public Integer getTotal_Number_of_question() {
        return total_Number_of_question;
    }

    public void setTotal_Number_of_question(Integer total_Number_of_question) {
        this.total_Number_of_question = total_Number_of_question;
    }

    public Boolean getIs_closed_Notification_On() {
        return is_closed_Notification_On;
    }

    public void setIs_closed_Notification_On(Boolean is_closed_Notification_On) {
        this.is_closed_Notification_On = is_closed_Notification_On;
    }

    public String getTest_Closed_date() {
        return test_Closed_date;
    }

    public void setTest_Closed_date(String test_Closed_date) {
        this.test_Closed_date = test_Closed_date;
    }

    public String getTest_Closed_Message() {
        return test_Closed_Message;
    }

    public void setTest_Closed_Message(String test_Closed_Message) {
        this.test_Closed_Message = test_Closed_Message;
    }

    public Boolean getIs_Test_category_Specail() {
        return is_Test_category_Specail;
    }

    public void setIs_Test_category_Specail(Boolean is_Test_category_Specail) {
        this.is_Test_category_Specail = is_Test_category_Specail;
    }

    public Boolean getIs_ReAttempt() {
        return is_ReAttempt;
    }

    public void setIs_ReAttempt(Boolean is_ReAttempt) {
        this.is_ReAttempt = is_ReAttempt;
    }

    public String getReAttempt_Error_Message() {
        return reAttempt_Error_Message;
    }

    public void setReAttempt_Error_Message(String reAttempt_Error_Message) {
        this.reAttempt_Error_Message = reAttempt_Error_Message;
    }

    public Boolean getIs_Instant_View_Rank() {
        return is_Instant_View_Rank;
    }

    public void setIs_Instant_View_Rank(Boolean is_Instant_View_Rank) {
        this.is_Instant_View_Rank = is_Instant_View_Rank;
    }

    public Boolean getIs_Instant_View_Result() {
        return is_Instant_View_Result;
    }

    public void setIs_Instant_View_Result(Boolean is_Instant_View_Result) {
        this.is_Instant_View_Result = is_Instant_View_Result;
    }

    public Boolean getIs_Notification() {
        return is_Notification;
    }

    public void setIs_Notification(Boolean is_Notification) {
        this.is_Notification = is_Notification;
    }

    public String getNotification_Message() {
        return notification_Message;
    }

    public void setNotification_Message(String notification_Message) {
        this.notification_Message = notification_Message;
    }

    public String getIsNegativeMarking_decode() {
        return isNegativeMarking_decode;
    }

    public void setIsNegativeMarking_decode(String isNegativeMarking_decode) {
        this.isNegativeMarking_decode = isNegativeMarking_decode;
    }

    public String getIsPremium_encode() {
        return isPremium_encode;
    }

    public void setIsPremium_encode(String isPremium_encode) {
        this.isPremium_encode = isPremium_encode;
    }

    public String getIsPremium_decode() {
        return isPremium_decode;
    }

    public void setIsPremium_decode(String isPremium_decode) {
        this.isPremium_decode = isPremium_decode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}