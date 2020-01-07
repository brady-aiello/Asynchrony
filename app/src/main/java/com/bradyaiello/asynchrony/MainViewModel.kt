package com.bradyaiello.asynchrony

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainViewModel : ViewModel() {
    val callNumber = MutableLiveData<Int>()
    val singleNumber = MutableLiveData<Int>()
    val stringNumber = MutableLiveData<Int>()

    val callResultMutableLiveData = MutableLiveData<String>()
    val singleResultMutableLiveData = MutableLiveData<String>()
    val stringResultMutableLiveData = MutableLiveData<String>()
    private val moshiConverter = MoshiConverterFactory.create()
    private val scalarsConverter = ScalarsConverterFactory.create()

    private val url = "http://numbersapi.com"
    val numbersServiceCall = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(scalarsConverter)
        .addConverterFactory(moshiConverter)
        .build()
        .create(NumbersServiceCall::class.java)

    val numbersServiceSingle = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(scalarsConverter)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(NumbersServiceSingle::class.java)

    val numbersServiceString = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(scalarsConverter)
        .build()
        .create(NumbersServiceString::class.java)

    fun getNumberFactCall(num: String) {
        numbersServiceCall.getNumber(num)
            .enqueue(object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    callResultMutableLiveData.postValue(t.localizedMessage)
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    callResultMutableLiveData.postValue(response.body())
                }
            })
    }

    fun getNumberSingle(num: String) {
        val disposable = numbersServiceSingle.getNumber(num)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ s -> singleResultMutableLiveData.postValue(s)}
    }

    fun getNumberString(num: String) {
        viewModelScope.launch {
            val result = numbersServiceString.getNumber(num)
            stringResultMutableLiveData.postValue(result)
        }
    }
}