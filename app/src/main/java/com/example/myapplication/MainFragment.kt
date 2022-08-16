package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    val viewModel: CustomerViewModel by viewModels()
    var counterOfList = 0

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
        val gson = Gson()
        val arrayCustomerType = object : TypeToken<Array<Customers>>() {}.type
        var arrayOfCustomer: Array<Customers> =
            gson.fromJson(viewModel.getJson(), arrayCustomerType)
        var sliceCustomer = ArrayList<Customers>()
        sliceCustomer.addAll(setData(arrayOfCustomer))
        val customerAdaptor = CustomAdapter(sliceCustomer)
        binding.customerRecyclerview.adapter = customerAdaptor
        var currentPageNumber = 0
        var allPageNumber = 0
        if(arrayOfCustomer.size % 10 == 0){
            allPageNumber = arrayOfCustomer.size / 10
        }else{
            println("sliceCustomer.size / 10 ${sliceCustomer.size / 10}")
            allPageNumber = (arrayOfCustomer.size / 10) + 1
        }
        viewModel.number.observe(viewLifecycleOwner) {
            binding.textViewCurrentPage.text = it.toString()
        }
       binding.textViewAllPage.text = allPageNumber.toString()


        fun clearListAddData() {
            sliceCustomer.clear()
            sliceCustomer.addAll(setData(arrayOfCustomer))
            customerAdaptor.notifyDataSetChanged()
        }
        if (counterOfList <= 0) {
            binding.buttonPerv.isEnabled = false
        }
        binding.buttonNext.setOnClickListener {
            counterOfList += 10
            binding.buttonPerv.isEnabled = true
            clearListAddData()
            if (counterOfList in (arrayOfCustomer.size-9)..arrayOfCustomer.size) {
                binding.buttonNext.isEnabled = false
            }
            viewModel.riseNumber()
        }
        binding.buttonPerv.setOnClickListener {
            counterOfList -= 10
            viewModel.decNumber()
            binding.buttonNext.isEnabled = true
            clearListAddData()
            if (counterOfList <= 0) {
                binding.buttonPerv.isEnabled = false
            }
        }


    }


    fun setData(array: Array<Customers>): Array<Customers> {
        println("counterOfList $counterOfList")
        println("array.size ${array.size}")
        if (counterOfList in (array.size-9)..array.size){
            return array.sliceArray(counterOfList..(counterOfList + (array.size % 10) -1))
        } else {
            return array.sliceArray(counterOfList..counterOfList + 9)
        }
    }


}