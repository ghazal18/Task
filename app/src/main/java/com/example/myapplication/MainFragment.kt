package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentMainBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() ,Listener {
    lateinit var binding: FragmentMainBinding
    private val viewModel: CustomerViewModel by viewModels()
    private var counterOfList = 0
    private var numberOfClassification = 8
    private lateinit var customerAdaptor: CustomAdapter
    val sliceCustomer = ArrayList<Customers>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gson = Gson()
        val arrayOfCustomer: Array<Customers> =
            gson.fromJson(viewModel.getJson(), Array<Customers>::class.java)
        var allPageNumber = 0
        viewModel.number.observe(viewLifecycleOwner) {
            binding.textViewCurrentPage.text = it.toString()
        }

        fun setInformation() {
            counterOfList = 0
            viewModel.makeOne()
            sliceCustomer.clear()
            sliceCustomer.addAll(setData(arrayOfCustomer))
            customerAdaptor = CustomAdapter(sliceCustomer,this)
            customerAdaptor.notifyDataSetChanged()
            binding.customerRecyclerview.adapter = customerAdaptor
            allPageNumber = if (arrayOfCustomer.size % numberOfClassification == 0) {
                arrayOfCustomer.size / numberOfClassification
            } else {
                (arrayOfCustomer.size / numberOfClassification) + 1
            }
            binding.textViewAllPage.text = allPageNumber.toString()

            binding.buttonPerv.isEnabled = counterOfList > 0

            binding.buttonNext.isEnabled = true



            binding.buttonNext.setOnClickListener {
                counterOfList += numberOfClassification
                binding.buttonPerv.isEnabled = true
                clearListAddData(sliceCustomer, arrayOfCustomer)
                if (counterOfList in (arrayOfCustomer.size - (numberOfClassification - 1))..arrayOfCustomer.size) {
                    binding.buttonNext.isEnabled = false
                }
                viewModel.riseNumber()
            }
            binding.buttonPerv.setOnClickListener {
                counterOfList -= numberOfClassification
                viewModel.decNumber()
                binding.buttonNext.isEnabled = true
                clearListAddData(sliceCustomer, arrayOfCustomer)
                if (counterOfList <= 0) {
                    binding.buttonPerv.isEnabled = false
                }
            }
        }

        setInformation()

        binding.editTextAlPage.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_GO)) {
                numberOfClassification = binding.editTextAlPage.text.toString().toInt()
                setInformation()
                true
            } else {
                false
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearListAddData(sliceCustomer: ArrayList<Customers>, arrayOfCustomer: Array<Customers>) {
        sliceCustomer.clear()
        sliceCustomer.addAll(setData(arrayOfCustomer))
        customerAdaptor.notifyDataSetChanged()
    }

    private fun setData(array: Array<Customers>): Array<Customers> {
        return if (counterOfList in (array.size - (numberOfClassification - 1))..array.size) {
            array.sliceArray(counterOfList until counterOfList + (array.size % numberOfClassification))
        } else {
            array.sliceArray(counterOfList..counterOfList + (numberOfClassification - 1))
        }
    }

    override fun onItemClick(name: String) {
        Toast.makeText(context, " $name clicked", Toast.LENGTH_SHORT).show()
    }


}