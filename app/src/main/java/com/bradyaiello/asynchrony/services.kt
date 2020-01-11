package com.bradyaiello.asynchrony

import com.bradyaiello.asynchrony.models.CatFact
import com.bradyaiello.asynchrony.models.CatFactJavaModelMoshi
import com.bradyaiello.asynchrony.models.CatFactModelGson
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

    @GET("facts/random?amount=1")
    suspend fun getRandomCatFact() : CatFact

    @GET("facts/random?amount=1")
    fun getRandomCatFactObservable(): Single<CatFact>

    @GET("facts/random")
    suspend fun getRandomCatFacts(@Query(value = "amount") amount: String) : List<CatFact>

    @GET("facts/random?amount=1")
    suspend fun getRandomCatFactJavaModelMoshi() : CatFactJavaModelMoshi

    @GET("facts/random?amount=1")
    suspend fun getRandomCatFactJavaModelGson() : CatFactModelGson
}

