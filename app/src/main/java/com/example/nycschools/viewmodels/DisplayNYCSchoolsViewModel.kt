package com.example.nycschools.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschools.repository.Repository
import com.example.nycschools.response.NYCSchoolItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayNYCSchoolsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableLiveData<Result<List<NYCSchoolItem>>>()
    val uiState: LiveData<Result<List<NYCSchoolItem>>>
        get() = _uiState

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean>
        get() = _isLoading

    init {
        getValidicDeviceList()
    }

   private fun getValidicDeviceList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = repository.getNYSSchoolsList()
                _uiState.value = Result.success(result)
                _isLoading.value = false
            } catch (exception: Exception) {
                _uiState.value = Result.failure(exception)
                _isLoading.value = false
            }
        }
    }
}