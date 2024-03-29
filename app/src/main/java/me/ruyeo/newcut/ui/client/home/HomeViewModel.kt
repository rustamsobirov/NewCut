package me.ruyeo.newcut.ui.client.home

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.local.enitity.Booking
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.data.model.Criteria
import me.ruyeo.newcut.model.map.GeoResponse
import me.ruyeo.newcut.model.map.Latlng
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import me.ruyeo.newcut.utils.extensions.Event
import me.ruyeo.newcut.utils.extensions.State
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    var fusedLocationClient: FusedLocationProviderClient,
) : ViewModel() {
    private var cancellationTokenSource = CancellationTokenSource()

    private var _address = MutableLiveData<String>()
    val address get() = _address

    private var _myLocation = MutableLiveData<Event<State<LatLng>>>()
    val myLocation get() = _myLocation

    private val _getBookingState = MutableStateFlow<UiStateList<Booking>>(UiStateList.EMPTY)
    val getBookingState = _getBookingState

    fun getAllBooking() = viewModelScope.launch {
        _getBookingState.value = UiStateList.LOADING
        try {
            val response = repository.getAllBooking()
            _getBookingState.value = UiStateList.SUCCESS(response)
        } catch (e: Exception) {
            _getBookingState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    fun addBooking(booking: Booking) = viewModelScope.launch {
        repository.addBooking(booking)
    }

    @SuppressLint("MissingPermission")
    fun requestCurrentLocation() {
        _myLocation.value = Event(State.LoadingState)
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result
                    val coordinate = LatLng(location.latitude, location.longitude)
                    _myLocation.value = Event(State.SuccessState(coordinate))
                } else {
                    _myLocation.value = Event(State.ErrorState(task.exception))
                }
            }
    }

    private var _getLocationName = MutableStateFlow<UiStateObject<GeoResponse>>(UiStateObject.EMPTY)
    val getLocationName = _getLocationName

    fun getLocationName(latLng: Latlng) = viewModelScope.launch {
        _getLocationName.value = UiStateObject.LOADING
        try {
            val response = repository.getLocationName(latLng = latLng)
            _getLocationName.value = UiStateObject.SUCCESS(response)
        } catch (e: Exception) {
            _getLocationName.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    private val _getBarbershopsState = MutableStateFlow<UiStateList<Barbershop>>(UiStateList.EMPTY)
    var getBarbershopState = _getBarbershopsState

    fun getBarberShops() = viewModelScope.launch {
        _getBarbershopsState.value = UiStateList.LOADING
        try {
            val response = repository.getAllBarbershops()
            if (response.success) {
                _getBarbershopsState.value = UiStateList.SUCCESS(response.data)
            } else {
                _getBarbershopsState.value = UiStateList.ERROR(response.error.message)
            }
        } catch (e: Exception) {
            _getBarbershopsState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    private val _criteriaState = MutableStateFlow<UiStateList<Barbershop>>(UiStateList.EMPTY)
    val criteriaState = _criteriaState

    fun getByCriteria(criteria: Criteria) = viewModelScope.launch {
        _criteriaState.value = UiStateList.LOADING
        try {
            val response = repository.getByCriteria(criteria)
            if (response.success){
                _criteriaState.value = UiStateList.SUCCESS(response.data)
            }else{
                _criteriaState.value = UiStateList.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _criteriaState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

}