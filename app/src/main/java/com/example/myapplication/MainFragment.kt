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
        val gson = Gson()
        val arrayCustomerType = object : TypeToken<Array<Customers>>() {}.type
        var arrayOfCustomer: Array<Customers> = gson.fromJson(viewModel.getJson(), arrayCustomerType)
        var sliceCustomer = ArrayList<Customers>()
        sliceCustomer.addAll(setData(arrayOfCustomer))
        val customerAdaptor = CustomAdapter(sliceCustomer)
        binding.customerRecyclerview.adapter = customerAdaptor
        fun clearListAddData() {
            sliceCustomer.clear()
            sliceCustomer.addAll(setData(arrayOfCustomer))
            customerAdaptor.notifyDataSetChanged()

        }
        if (i <= 0) {
            binding.buttonPerv.isEnabled = false
        }
        binding.buttonNext.setOnClickListener {
            i += 10
            binding.buttonPerv.isEnabled = true
            clearListAddData()
            if (i >= arrayOfCustomer.size - 11) {
                binding.buttonNext.isEnabled = false
            }
        }
        binding.buttonPerv.setOnClickListener {
            i -= 10
            binding.buttonNext.isEnabled = true
            clearListAddData()
            if (i <= 0) {
                binding.buttonPerv.isEnabled = false
            }
        }


    }


    fun setData(y: Array<Customers>): Array<Customers> {
        return y.sliceArray(i..i + 9)
    }


}