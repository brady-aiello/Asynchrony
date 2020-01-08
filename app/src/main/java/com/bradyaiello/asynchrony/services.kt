package com.bradyaiello.asynchrony

import com.bradyaiello.asynchrony.models.CatFact
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


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

interface CatFactService {
    @GET("fact/random?amount=1")
    suspend fun getRandomCatFact() : CatFact

    @GET("fact/random")
    suspend fun getRandomCatFact(@Query(value = "amount") amount: String) : List<CatFact>
}