package com.winbee.successcentersikar.NewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detail implements Serializable {

@SerializedName("bucket_name")
@Expose
private String bucket_name;
@SerializedName("bucket_content_video")
@Expose
private Integer bucket_content_video;
@SerializedName("bucket_content_notes")
@Expose
private Integer bucket_content_notes;

public String getBucket_name() {
return bucket_name;
}

public void setBucket_name(String bucket_name) {
this.bucket_name = bucket_name;
}

public Integer getBucket_content_video() {
return bucket_content_video;
}

public void setBucket_content_video(Integer bucket_content_video) {
this.bucket_content_video = bucket_content_video;
}

public Integer getBucket_content_notes() {
return bucket_content_notes;
}

public void setBucket_content_notes(Integer bucket_content_notes) {
this.bucket_content_notes = bucket_content_notes;
}

}