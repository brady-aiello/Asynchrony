package com.bradyaiello.asynchrony.numberfact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bradyaiello.asynchrony.R
import com.bradyaiello.asynchrony.databinding.ActivityNumberFactBinding

class NumberFactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityNumberFactBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_number_fact
        )
        val viewModel = ViewModelProviders.of(this)[NumberFactActivityViewModel::class.java]
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.asyncTaskButton.setOnClickListener {
            viewModel.getNumberFactAsyncTask(binding.asyncTaskET.text.toString())
        }

        binding.callButton.setOnClickListener {
            viewModel.getNumberFactCall(binding.callET.text.toString())
        }
        binding.singleButton.setOnClickListener {
            viewModel.getNumberFactSingle(binding.singleET.text.toString())
        }
        binding.stringButton.setOnClickListener {
            viewModel.getNumberFactString(binding.stringET.text.toString())
        }

        viewModel.asyncTaskResultMutableLiveData.observe(this, Observer<String> {
            binding.asyncTaskResult.text = it
        })
        viewModel.callResultMutableLiveData.observe(this, Observer<String> {
            binding.callResult.text = it
        })
        viewModel.singleResultMutableLiveData.observe(this, Observer<String> {
            binding.singleResult.text = it
        })
        viewModel.stringResultMutableLiveData.observe(this, Observer<String> {
            binding.stringResult.text = it
        })
    }
}
