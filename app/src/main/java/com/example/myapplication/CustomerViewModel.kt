package com.example.myapplication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CustomerViewModel @Inject constructor(val customerRepository: Repository) : ViewModel() {
    init {
        getJson()
    }

    fun getJson(): String {
        return customerRepository.customerJson
    }
}