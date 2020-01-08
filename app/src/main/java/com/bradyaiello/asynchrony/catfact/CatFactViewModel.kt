package com.bradyaiello.asynchrony.catfact

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradyaiello.asynchrony.CatFactService
import com.bradyaiello.asynchrony.models.CatFact
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatFactViewModel : ViewModel() {
    private val TAG = CatFactViewModel::class.java.toString()
    val catFactLiveData = MutableLiveData<CatFact>()
    private val url = "https://cat-fact.herokuapp.com"
    private val loggingInterceptor = HttpLoggingInterceptor()
    private var catFactService : CatFactService
    private var httpClient : OkHttpClient
    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        catFactService  = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CatFactService::class.java)
    }

    fun getCatFact() {
        viewModelScope.launch {
            try {
                val catFact = catFactService.getRandomCatFact()
                catFactLiveData.postValue(catFact)
            } catch (e: HttpException) {
                Log.e(TAG, e.toString())
            }

        }
    }
}