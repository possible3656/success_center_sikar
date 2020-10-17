package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AboutModel implements Serializable {

@SerializedName("Message")
@Expose
private String message;
@SerializedName("Banner")
@Expose
private String banner;

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getBanner() {
return banner;
}

public void setBanner(String banner) {
this.banner = banner;
}

}