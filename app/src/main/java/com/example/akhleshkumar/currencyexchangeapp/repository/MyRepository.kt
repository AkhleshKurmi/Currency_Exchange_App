package com.example.akhleshkumar.currencyexchangeapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.akhleshkumar.currencyexchangeapp.retrofit.RetrofitClient
import com.example.akhleshkumar.currencyexchangeapp.sqlitedb.MySQLiteDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRepository(val context : Context) {

    var currencyDataList = MutableLiveData<List<String>>()
    var exchangeData = MutableLiveData<Double>()
    var mySQLiteDatabase = MySQLiteDatabase(context)
    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = true
    }
    var isExchanging: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    fun checkData() : MutableLiveData<List<String>>{
        if (mySQLiteDatabase.fetchList().isEmpty()){
           getCurrencyList()
        }else{
           currencyDataList.value = mySQLiteDatabase.fetchList()
            isLoading.value = false

        }
        return currencyDataList
    }


    fun getCurrencyList(){
        val call = RetrofitClient.apiCall.getCurrencyList(/*apiKey, host*/)

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                if (response.isSuccessful) {
                    mySQLiteDatabase.saveList(response.body()!!)
                    checkData()
                } else {
                    Log.d("datasize1", "onResponsecode: ${response.code()} ")
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("datasize1", "onResponse Cause: ${t.cause} ")
                Log.d("datasize1", "onResponse message: ${t.message} ")
            }
        })
    }

    fun exchangedCurrency(from: String, to: String, quantity: Double): MutableLiveData<Double> {
        var call = RetrofitClient.apiCall.getConvertResult(from, to, quantity)

        call.enqueue(object : Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>) {
                if (response.isSuccessful) {
                    exchangeData.value = response.body()
                    isExchanging.value = false
                } else {

                }
            }

            override fun onFailure(call: Call<Double>, t: Throwable) {

            }

        })
        return exchangeData
    }


}