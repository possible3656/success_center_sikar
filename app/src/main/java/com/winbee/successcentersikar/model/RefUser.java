package com.winbee.successcentersikar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RefUser implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("refcode")
    @Expose
    private String refcode;
    @SerializedName("DeviceId")
    @Expose
    private String deviceId;
    @SerializedName("ReferralCode")
    @Expose
    private String referralCode;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("registration_id")
    @Expose
    private String registration_id;
    @SerializedName("OTP")
    @Expose
    private String oTP;
    @SerializedName("ReferredStatus")
    @Expose
    private Boolean referredStatus;
    @SerializedName("ReferredMessage")
    @Expose
    private String referredMessage;
    @SerializedName("ReferralCodeSelf")
    @Expose
    private String referralCodeSelf;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("OTPMessage")
    @Expose
    private String oTPMessage;

    public RefUser(String name, String mobile, String email, String password, String refcode ,String referralCodeSelf ) {
        this.name = name;
        this.mobile = mobile;
        this.email=email;
        this.password=password;
        this.refcode=refcode;
        this.referralCodeSelf=referralCodeSelf;

    }

    public RefUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefcode() {
        return refcode;
    }

    public void setRefcode(String refcode) {
        this.refcode = refcode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }

    public String getOTP() {
        return oTP;
    }

    public void setOTP(String oTP) {
        this.oTP = oTP;
    }

    public Boolean getReferredStatus() {
        return referredStatus;
    }

    public void setReferredStatus(Boolean referredStatus) {
        this.referredStatus = referredStatus;
    }

    public String getReferredMessage() {
        return referredMessage;
    }

    public void setReferredMessage(String referredMessage) {
        this.referredMessage = referredMessage;
    }

    public String getReferralCodeSelf() {
        return referralCodeSelf;
    }

    public void setReferralCodeSelf(String referralCodeSelf) {
        this.referralCodeSelf = referralCodeSelf;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOTPMessage() {
        return oTPMessage;
    }

    public void setOTPMessage(String oTPMessage) {
        this.oTPMessage = oTPMessage;
    }

}