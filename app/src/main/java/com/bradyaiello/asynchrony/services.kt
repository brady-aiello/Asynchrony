package com.bradyaiello.asynchrony

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface NumberFactsService {
    // Good
    @GET("{number}")
    fun getNumberFactCall(@Path (value = "number") number: String) : Call<String>

    // Better
    @GET("{number}")
    fun getNumberFactSingle(@Path (value = "number") number: String) : Single<String>

    // Best
    @GET("{number}")
    suspend fun getNumberFactString(@Path (value = "number") number: String) : String
}