package com.dineshworkspace.fipanvalidator.pan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dineshworkspace.fipanvalidator.PanCardValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PanViewModel @Inject constructor(private val panCardValidator: PanCardValidator) :
    ViewModel() {

    val isValidPan: LiveData<Boolean> get() = _isValidPan
    private val _isValidPan: MutableLiveData<Boolean> = MutableLiveData(false)

    val isValidYear: MutableLiveData<Boolean> get() = _isValidYear
    private val _isValidYear: MutableLiveData<Boolean> = MutableLiveData(false)

    fun validatePanCard(panData: String) {
        viewModelScope.launch {
            _isValidPan.postValue(panCardValidator.validatePanCard(panData))
        }
    }

    fun validateBirthYear(year: String) {
        viewModelScope.launch {
            if (year.isNotEmpty() &&
                year.toInt() in 1901..2998
            ) {
                isValidYear.postValue(true)
            } else {
                isValidYear.postValue(false)
            }
        }
    }

}