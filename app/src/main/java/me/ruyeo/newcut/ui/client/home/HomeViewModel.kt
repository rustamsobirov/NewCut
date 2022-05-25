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
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
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
    private val getBookingState = _getBookingState

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

}