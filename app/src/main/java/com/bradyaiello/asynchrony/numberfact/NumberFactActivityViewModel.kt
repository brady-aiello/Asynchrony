package com.bradyaiello.asynchrony.numberfact

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bradyaiello.asynchrony.NumberFactsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception

class NumberFactActivityViewModel : ViewModel() {

    val asyncTaskNumber = MutableLiveData<Int>()
    val callNumber = MutableLiveData<Int>()
    val singleNumber = MutableLiveData<Int>()
    val stringNumber = MutableLiveData<Int>()

    val asyncTaskResultMutableLiveData = MutableLiveData<String>()
    val callResultMutableLiveData = MutableLiveData<String>()
    val singleResultMutableLiveData = MutableLiveData<String>()
    val stringResultMutableLiveData = MutableLiveData<String>()
    private val scalarsConverter = ScalarsConverterFactory.create()

    private val url = "http://numbersapi.com"
    private val TAG = NumberFactActivity::class.java.canonicalName.toString()
    private val numberFactsService : NumberFactsService = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(scalarsConverter) // String Return Type
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
                asyncTaskResultMutableLiveData.postValue(response.body?.string())
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
            .subscribe(
                { s -> singleResultMutableLiveData.postValue(s)},
                { e -> Log.e(TAG, e.toString())}
            )
    }

    fun getNumberFactString(num: String) {
        val deferred = viewModelScope.launch {
            try {
                val result = numberFactsService.getNumberFactString(num)
                stringResultMutableLiveData.postValue(result)
            } catch (e: HttpException) {
                Log.e(TAG, e.toString())
            }
        }
    }
}