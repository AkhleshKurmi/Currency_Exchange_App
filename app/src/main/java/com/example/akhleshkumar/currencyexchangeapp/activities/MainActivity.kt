package com.example.akhleshkumar.currencyexchangeapp.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.akhleshkumar.currencyexchangeapp.R
import com.example.akhleshkumar.currencyexchangeapp.databinding.ActivityMainBinding
import com.example.akhleshkumar.currencyexchangeapp.factory.MyViewModelFactory
import com.example.akhleshkumar.currencyexchangeapp.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myViewModel: MyViewModel
    lateinit var from: String
    lateinit var to: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        myViewModel = ViewModelProvider(this, MyViewModelFactory(this))[MyViewModel::class.java]
        val data = intent
        val arrayList = data.getStringArrayListExtra("arrayList")
        val mutableArrayList: MutableList<String> = arrayList as MutableList<String>
        val arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableArrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerFrom.adapter = arrayAdapter
        binding.spinnerTo.adapter = arrayAdapter

        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, p3: Long
            ) {
                from = parent!!.getItemAtPosition(position).toString()
                binding.tilExchangeQuantity.suffixText = from
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        binding.spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, p3: Long
            ) {

                to = parent!!.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        binding.btnExchange.setOnClickListener {
            if (binding.etQuantity.text!!.isNotEmpty()) {
                if (from.isNotEmpty() && to.isNotEmpty()) {
                    val quantity = binding.etQuantity.text.toString().toDouble()
                    myViewModel.myRepository.isExchanging.value = true
                    myViewModel.getExchangeData(from, to, quantity).observe(this) {
                        binding.tvExchangeResult.text = "${it*quantity} $to"
                    }
                } else {
                    Toast.makeText(this, "Please Select Currencies", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.etQuantity.error = "Fill Quantity"
            }
        }


        myViewModel.isConverting.observe(this){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }



}