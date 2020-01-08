package com.bradyaiello.asynchrony

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bradyaiello.asynchrony.catfact.CatFactActivity
import com.bradyaiello.asynchrony.numberfact.NumberFactActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToNumberFactActivity.setOnClickListener {
           startActivity(Intent(this, NumberFactActivity::class.java))
        }
        goToCatFactActivity.setOnClickListener {
            startActivity(Intent(this, CatFactActivity::class.java))
        }
    }
}
