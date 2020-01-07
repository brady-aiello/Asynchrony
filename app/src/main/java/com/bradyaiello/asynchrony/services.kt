package com.bradyaiello.asynchrony

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Good
interface NumberFactsService {

    @GET("{number}")
    fun getNumberFactAsyncTask(@Path (value = "number") number: String) : Response<String>

    @GET("{number}")
    fun getNumberFactCall(@Path (value = "number") number: String) : Call<String>

    @GET("{number}")
    fun getNumberFactSingle(@Path (value = "number") number: String) : Single<String>

    @GET("{number}")
    suspend fun getNumberFactString(@Path (value = "number") number: String) : String
}