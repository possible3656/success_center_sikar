package com.winbee.successcentersikar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveStatus implements Serializable {

@SerializedName("livestatus")
@Expose
private Boolean livestatus;

public Boolean getLivestatus() {
return livestatus;
}

public void setLivestatus(Boolean livestatus) {
this.livestatus = livestatus;
}

}