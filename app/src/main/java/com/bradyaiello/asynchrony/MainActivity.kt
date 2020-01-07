package com.bradyaiello.asynchrony

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bradyaiello.asynchrony.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.callButton.setOnClickListener {
            viewModel.getNumberFactCall(binding.callET.text.toString())
        }
        binding.singleButton.setOnClickListener {
            viewModel.getNumberSingle(binding.singleET.text.toString())
        }
        binding.stringButton.setOnClickListener {
            viewModel.getNumberString(binding.stringET.text.toString())
        }

        viewModel.callResultMutableLiveData.observe(this, Observer<String> { fact ->
            binding.callResult.text = fact
        })
        viewModel.singleResultMutableLiveData.observe(this, Observer<String> { fact ->
            binding.singleResult.text = fact
        })
        viewModel.stringResultMutableLiveData.observe(this, Observer<String> { fact ->
            binding.stringResult.text = fact
        })

    }
}
