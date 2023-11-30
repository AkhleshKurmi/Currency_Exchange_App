package com.example.akhleshkumar.currencyexchangeapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.akhleshkumar.currencyexchangeapp.repository.MyRepository

class MyViewModel(private val context: Context) :ViewModel() {

    var myRepository = MyRepository(context)
    var isloading :MutableLiveData<Boolean> =myRepository.isLoading
    var isConverting :MutableLiveData<Boolean> = myRepository.isExchanging
//    var getList : MutableLiveData<CurrencyList> = myRepository.getCurrencyList()
    fun getCurrency(): MutableLiveData<List<String>>{
        return myRepository.checkData()
    }

    fun getExchangeData(from: String, to: String, quantity: Double) : MutableLiveData<Double>{
        return myRepository.exchangedCurrency(from, to, quantity)
    }
}