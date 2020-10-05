package com.winbee.successcentersikar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NameUpdateModel implements Serializable {

@SerializedName("UserId")
@Expose
private String userId;
@SerializedName("Email")
@Expose
private String email;
@SerializedName("Name")
@Expose
private String name;
@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private Object message;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public Object getMessage() {
return message;
}

public void setMessage(Object message) {
this.message = message;
}

}