package com.bradyaiello.asynchrony.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusModelGson {
    @SerializedName("verified")
    @Expose
    public Boolean verified;
    @SerializedName("sentCount")
    @Expose
    public Integer sentCount;
}
