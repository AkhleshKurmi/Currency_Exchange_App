package com.example.akhleshkumar.currencyexchangeapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.akhleshkumar.currencyexchangeapp.R
import com.example.akhleshkumar.currencyexchangeapp.databinding.ActivitySpliceBinding
import com.example.akhleshkumar.currencyexchangeapp.factory.MyViewModelFactory
import com.example.akhleshkumar.currencyexchangeapp.viewmodel.MyViewModel

class SplashActivity : AppCompatActivity() {
    lateinit var binding :ActivitySpliceBinding
    lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splice)



        myViewModel = ViewModelProvider(this, MyViewModelFactory(this))[MyViewModel::class.java]

        val intent = Intent(this, MainActivity::class.java)
        myViewModel.getCurrency().observe(this) {
            intent.putExtra("arrayList", it as ArrayList<String>)

        }
//        var progressDialog = ProgressDialog(this)
//        progressDialog.setCancelable(false)
//        progressDialog.setMessage("Loading, Please wait...")

        myViewModel.isloading.observe(this, Observer {
            if (it) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.INVISIBLE
                startActivity(intent)
            }
        })

    }
    fun goOnMainActivity(){


        }

    }
