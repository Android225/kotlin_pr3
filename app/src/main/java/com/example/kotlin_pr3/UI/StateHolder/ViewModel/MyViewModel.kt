package com.example.kotlin_pr3.UI.StateHolder.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_pr3.Data.Repository.DataRepository

class MyViewModel : ViewModel() {
    private val dataRepository = DataRepository()
    val data: LiveData<String> = dataRepository.getData()
}