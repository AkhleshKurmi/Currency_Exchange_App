package com.example.akhleshkumar.currencyexchangeapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.akhleshkumar.currencyexchangeapp.viewmodel.MyViewModel

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)){
            return MyViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}