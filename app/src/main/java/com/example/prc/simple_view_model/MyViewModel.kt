package com.example.prc.simple_view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    val list = ArrayList<Int>()

    val currentName by lazy {
        MutableLiveData<ArrayList<Int>>()
    }

    fun add(int: Int) {
        list.add(1)
        currentName.value = list
    }
}