package com.example.rickandmortytest.presentation.ui.location

import androidx.paging.PagingData
import com.example.rickandmortytest.data.model.LocationEntity
import com.example.rickandmortytest.data.model.LocationsEntity
import com.example.rickandmortytest.domain.model.Location
import com.example.rickandmortytest.domain.usecases.location.GetLocations
import com.example.rickandmortytest.presentation.base.BaseViewModel
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationsViewModel(private val getLocationUseCase: GetLocations) : BaseViewModel() {

    private val _getLocationState = MutableStateFlow<UIState<PagingData<Location>>>(UIState.Empty())
    val getLocationState = _getLocationState.asStateFlow()

    fun getLocations(name: String? = null) {
        getLocationUseCase(name).collectFlow(_getLocationState)
    }

    fun invalidate() {
        _getLocationState.value = UIState.Empty()
    }

}