package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
    var numberOfClassification = 8

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
        var allPageNumber = 0
        fun setInformation() {
            var sliceCustomer = ArrayList<Customers>()
            sliceCustomer.addAll(setData(arrayOfCustomer))
            val customerAdaptor = CustomAdapter(sliceCustomer)
            binding.customerRecyclerview.adapter = customerAdaptor
            if (arrayOfCustomer.size % numberOfClassification == 0) {
                allPageNumber = arrayOfCustomer.size / numberOfClassification
            } else {
                allPageNumber = (arrayOfCustomer.size / numberOfClassification) + 1
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
                counterOfList += numberOfClassification
                binding.buttonPerv.isEnabled = true
                clearListAddData()
                if (counterOfList in (arrayOfCustomer.size - (numberOfClassification - 1))..arrayOfCustomer.size) {
                    binding.buttonNext.isEnabled = false
                }
                viewModel.riseNumber()
            }
            binding.buttonPerv.setOnClickListener {
                counterOfList -= numberOfClassification
                viewModel.decNumber()
                binding.buttonNext.isEnabled = true
                clearListAddData()
                if (counterOfList <= 0) {
                    binding.buttonPerv.isEnabled = false
                }
            }
        }
        setInformation()

        binding.editTextAlPage.setOnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                numberOfClassification = binding.editTextAlPage.text.toString().toInt()
                setInformation()
                true
            } else {
                false
            }
        }
    }


    fun setData(array: Array<Customers>): Array<Customers> {
        println("counterOfList $counterOfList")
        println("array.size ${array.size}")
        if (counterOfList in (array.size - (numberOfClassification - 1))..array.size) {
            return array.sliceArray(counterOfList..(counterOfList + (array.size % numberOfClassification) - 1))
        } else {
            return array.sliceArray(counterOfList..counterOfList + (numberOfClassification - 1))
        }
    }


}