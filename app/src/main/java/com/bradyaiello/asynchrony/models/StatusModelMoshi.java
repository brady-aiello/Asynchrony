package com.bradyaiello.asynchrony.models;

import com.squareup.moshi.Json;

public class StatusModelMoshi {
    @Json(name = "verified")
    public Boolean verified;
    @Json(name = "sentCount")
    public Integer sentCount;

}
