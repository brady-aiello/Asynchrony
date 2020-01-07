package com.bradyaiello.asynchrony

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

// Good
interface NumbersServiceCall {
    @GET("{number}")
    fun getNumber(@Path (value = "number") number: String) : Call<String>
}

// Better
interface NumbersServiceSingle {
    @GET("{number}")
    fun getNumber(@Path (value = "number") number: String) : Single<String>
}

// Best
interface NumbersServiceString {
    @GET("{number}")
    suspend fun getNumber(@Path (value = "number") number: String) : String
}