package com.bradyaiello.asynchrony

import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainViewModel : ViewModel() {

    val asyncTaskNumber = MutableLiveData<Int>()
    val callNumber = MutableLiveData<Int>()
    val singleNumber = MutableLiveData<Int>()
    val stringNumber = MutableLiveData<Int>()

    val asyncTaskResultMutableLiveData = MutableLiveData<String>()
    val callResultMutableLiveData = MutableLiveData<String>()
    val singleResultMutableLiveData = MutableLiveData<String>()
    val stringResultMutableLiveData = MutableLiveData<String>()
    private val moshiConverter = MoshiConverterFactory.create()
    private val scalarsConverter = ScalarsConverterFactory.create()

    private val url = "http://numbersapi.com"
    val numberFactsService = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(scalarsConverter) // String Return Type
        .addConverterFactory(moshiConverter)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NumberFactsService::class.java)

    fun getNumberFactAsyncTask(num: String) {
        class NumberAsyncTask : AsyncTask<String, Void, Int>() {
            override fun doInBackground(vararg params: String): Int {
                val number = params[0]
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("$url/$number")
                    .get()
                    .build();
                val response = client.newCall(request).execute()
                asyncTaskResultMutableLiveData.postValue(response.body()?.string())
                return 0
            }
        }
        val numberAsyncTask = NumberAsyncTask()
        numberAsyncTask.execute(num)
    }

    fun getNumberFactCall(num: String) {
        numberFactsService.getNumberFactCall(num)
            .enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    callResultMutableLiveData.postValue(t.localizedMessage)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    callResultMutableLiveData.postValue(response.body())
                }
            })
    }

    fun getNumberFactSingle(num: String) {
        val disposable = numberFactsService.getNumberFactSingle(num)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ s -> singleResultMutableLiveData.postValue(s)}
    }

    fun getNumberFactString(num: String) {
        viewModelScope.launch {
            val result = numberFactsService.getNumberFactString(num)
            stringResultMutableLiveData.postValue(result)
        }
    }
}