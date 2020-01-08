package com.bradyaiello.asynchrony.catfact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bradyaiello.asynchrony.R
import com.bradyaiello.asynchrony.models.CatFact
import kotlinx.android.synthetic.main.activity_cat_fact.*

class CatFactActivity : AppCompatActivity() {
    lateinit var viewModel: CatFactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_fact)
        viewModel = ViewModelProviders.of(this)[CatFactViewModel::class.java]
        getCatFactButton.setOnClickListener {
            viewModel.getCatFact()
        }
        viewModel.catFactLiveData.observe(this, Observer<CatFact> {
            catFactTV.text = it.text
        })
    }
}
