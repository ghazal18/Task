package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CustomerViewModel @Inject constructor(val customerRepository: Repository) : ViewModel() {
    var number = MutableLiveData(1)
    init {
        getJson()
    }

    fun getJson(): String {
        return customerRepository.customerJson
    }
    fun riseNumber(){
        number.value = number.value?.plus(1)
    }
    fun decNumber(){
        number.value = number.value?.minus(1)
    }
    fun makeOne(){
        number.value = 1
    }
}