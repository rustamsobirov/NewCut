package me.ruyeo.newcut.ui.client.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.local.enitity.Booking
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.extensions.userMessage
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _getBookingState = MutableStateFlow<UiStateList<Booking>>(UiStateList.EMPTY)
    private val getBookingState = _getBookingState

    fun getAllBooking() = viewModelScope.launch {
        _getBookingState.value = UiStateList.LOADING
        try {
            val response = repository.getAllBooking()
            _getBookingState.value = UiStateList.SUCCESS(response)
        }catch (e: Exception){
            _getBookingState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    fun addBooking(booking: Booking) = viewModelScope.launch {
        repository.addBooking(booking)
    }
}