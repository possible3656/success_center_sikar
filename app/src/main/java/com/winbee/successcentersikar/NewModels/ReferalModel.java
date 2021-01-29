package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReferalModel implements Serializable {

@SerializedName("MyCode")
@Expose
private String myCode;
@SerializedName("Total_Points")
@Expose
private Integer total_Points;
@SerializedName("Total_Referral")
@Expose
private String total_Referral;
@SerializedName("Terms_Conditions")
@Expose
private String terms_Conditions;
@SerializedName("How_it_works")
@Expose
private String how_it_works;
@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private String message;
@SerializedName("service_up")
@Expose
private Boolean service_up;
@SerializedName("service_message")
@Expose
private String service_message;

public String getMyCode() {
return myCode;
}

public void setMyCode(String myCode) {
this.myCode = myCode;
}

public Integer getTotal_Points() {
return total_Points;
}

public void setTotal_Points(Integer total_Points) {
this.total_Points = total_Points;
}

public String getTotal_Referral() {
return total_Referral;
}

public void setTotal_Referral(String total_Referral) {
this.total_Referral = total_Referral;
}

public String getTerms_Conditions() {
return terms_Conditions;
}

public void setTerms_Conditions(String terms_Conditions) {
this.terms_Conditions = terms_Conditions;
}

public String getHow_it_works() {
return how_it_works;
}

public void setHow_it_works(String how_it_works) {
this.how_it_works = how_it_works;
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

public Boolean getService_up() {
return service_up;
}

public void setService_up(Boolean service_up) {
this.service_up = service_up;
}

public String getService_message() {
return service_message;
}

public void setService_message(String service_message) {
this.service_message = service_message;
}

}