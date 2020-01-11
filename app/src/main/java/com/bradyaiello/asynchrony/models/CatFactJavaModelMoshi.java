package com.bradyaiello.asynchrony.models;

import com.squareup.moshi.Json;

public class CatFactJavaModelMoshi {
    @Json(name = "used")
    public Boolean used;
    @Json(name = "source")
    public String source;
    @Json(name = "type")
    public String type;
    @Json(name = "deleted")
    public Boolean deleted;
    @Json(name = "_id")
    public String id;
    @Json(name = "updatedAt")
    public String updatedAt;
    @Json(name = "createdAt")
    public String createdAt;
    @Json(name = "user")
    public String user;
    @Json(name = "text")
    public String text;
    @Json(name = "__v")
    public Integer v;
    @Json(name = "status")
    public StatusModelMoshi statusModelMoshi;
}

