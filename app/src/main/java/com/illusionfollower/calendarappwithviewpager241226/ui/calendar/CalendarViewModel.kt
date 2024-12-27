package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
    private val _screenHeight = MutableLiveData<Int>()
    val screenHeight: LiveData<Int> = _screenHeight

    private val _dateHeight = MediatorLiveData<Int>().apply {
        addSource(screenHeight) { height ->
            value = height / 6
        }
    }
    val dateHeight: LiveData<Int> = _dateHeight


    fun setScreenHeight(height: Int) {
        _screenHeight.value = height
    }
}