package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val customerJson = """
               [{
        		"Active": true,
        		"Code": "2115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": "1 کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "999115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 2کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "2115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 3کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "999115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 4کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "2115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 5کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "999115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": "6 کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "2115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 7کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "999115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 8کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "2115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 9کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	},{
        		"Active": true,
        		"Code": "999115876",
        		"FirstCompanyName": " کلینیک تخصصی رضایی",
        		"FromToNameAndCompany": " کلینیک تخصصی رضایی ",
        		"Gender": true,
        		"ID": 11457,
        		"IsCompany": false,
        		"Name": " 10کلینیک تخصصی رضایی ",
        		"NameEng": " ",
        		"ProductID": 3,
        		"RolesName": "مشتری"
        	}]"""


        val gson = Gson()
        val arrayCustomerType = object : TypeToken<Array<Customer>>() {}.type
        var arrayOfCustomer: Array<Customer> = gson.fromJson(customerJson, arrayCustomerType)
        var sliceCustomer = ArrayList<Customer>()
        val customeradaptor = CustomAdapter(sliceCustomer)
        binding.customerRecyclerview.adapter = customeradaptor
        binding.buttonNext.setOnClickListener {
            i = i + 2
            setData(arrayOfCustomer)
            println(setData(arrayOfCustomer)[0].Name)
            println(setData(arrayOfCustomer)[1].Name)
            sliceCustomer.clear()
            sliceCustomer.addAll(setData(arrayOfCustomer))
            customeradaptor.notifyDataSetChanged()
        }


    }

    fun setData(y: Array<Customer>): Array<Customer> {
        println("the i is $i")
        return y.sliceArray(i..i + 1)
    }


}