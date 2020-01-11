package com.bradyaiello.asynchrony.catfact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bradyaiello.asynchrony.R
import com.bradyaiello.asynchrony.models.CatFact
import com.uber.autodispose.AutoDispose
import kotlinx.android.synthetic.main.activity_cat_fact.*

class CatFactActivity : AppCompatActivity() {
    private val TAG = CatFactActivity::class.java.toString()
    lateinit var viewModel: CatFactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_fact)
        viewModel = ViewModelProviders.of(this)[CatFactViewModel::class.java]
        getCatFactButton.setOnClickListener {
            viewModel.getCatFact()
        }
        getCatFactButtonRxJava.setOnClickListener {
            viewModel.getCatFactObservable()
        }

        getCatFactButtonJavaMoshiModel.setOnClickListener {
            viewModel.getCatFactJavaModelMoshi()
        }
        getCatFactButtonJavaGsonModel.setOnClickListener {
            viewModel.getCatFactJavaModelGson()
        }
        viewModel.catFactMutableLiveData.observe(this, Observer<CatFact> {
            catFactTV.text = it.text
        })
        viewModel.catFactLiveData.observe(this, Observer<CatFact> {
            catFactStartUpTV.text = it.text
        })
        viewModel.catFactStringMutableLiveData.observe(this, Observer<String> {
            catFactTV.text = it
        })
        viewModel.catFactStringMutableLiveData.observe(this, Observer<String> {
            catFactTV.text = it
        })
    }
}
