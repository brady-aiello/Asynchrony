package com.bradyaiello.asynchrony.catfact

import android.util.Log
import androidx.lifecycle.*
import com.bradyaiello.asynchrony.CatFactService
import com.bradyaiello.asynchrony.models.CatFact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.net.UnknownHostException

class CatFactViewModel : ViewModel() {
    private val TAG = CatFactViewModel::class.java.toString()
    var catFactMutableLiveData = MutableLiveData<CatFact>()
    lateinit var catFactLiveData: LiveData<CatFact>
    private val url = "https://cat-fact.herokuapp.com"
    private val loggingInterceptor = HttpLoggingInterceptor()
    private var catFactService : CatFactService
    private var httpClient : OkHttpClient

    var catFactToDispose : Disposable? = null
    var catFactStringMutableLiveData = MutableLiveData<String>()
    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        catFactService = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // To convert to Observables
            .addConverterFactory(MoshiConverterFactory.create()) // To convert JSON to CatFact / CatFactJavaModel
            .build()
            .create(CatFactService::class.java)

        catFactLiveData = liveData {
            try {
                val catFact = catFactService.getRandomCatFact()
                emit(catFact)
            } catch (e: UnknownHostException) {
                Log.e(TAG, e.toString())
            } catch (e: HttpException) {
                Log.e(TAG, e.message())
            } catch (e: IOException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun getCatFact() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val catFact = catFactService.getRandomCatFact()
                catFactMutableLiveData.postValue(catFact)
            } catch (e: UnknownHostException) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun getCatFactObservable() {
        catFactToDispose = catFactService.getRandomCatFactObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { t -> t.text }
            .subscribe(
                { s -> catFactStringMutableLiveData.postValue(s)},
                { e -> Log.e(TAG, e.toString())}
            )
    }
    fun getCatFactJavaModelMoshi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val catFact = catFactService.getRandomCatFactJavaModelMoshi()
                catFactStringMutableLiveData.postValue(catFact.text)
            } catch (e: UnknownHostException) {
                Log.e(TAG, e.toString())
            }
        }
    }
    fun getCatFactJavaModelGson() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val catFact = catFactService.getRandomCatFactJavaModelGson()
                catFactStringMutableLiveData.postValue(catFact.text)
            } catch (e: UnknownHostException) {
                Log.e(TAG, e.toString())
            }
        }
    }
}