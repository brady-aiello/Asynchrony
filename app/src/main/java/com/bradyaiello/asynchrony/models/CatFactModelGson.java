package com.bradyaiello.asynchrony.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CatFactModelGson {
    @SerializedName("used")
    @Expose
    public Boolean used;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("deleted")
    @Expose
    public Boolean deleted;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("updatedAt")
    @Expose
    public String updatedAt;
    @SerializedName("createdAt")
    @Expose
    public String createdAt;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("__v")
    @Expose
    public Integer v;
    @SerializedName("status")
    @Expose
    public StatusModelGson statusModelGson;

}
