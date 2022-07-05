package me.ruyeo.newcut.ui.client.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.data.model.Order
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _getOrderState = MutableStateFlow<UiStateList<Barbershop>>(UiStateList.EMPTY)
    val getOrderState = _getOrderState

    fun getOrders(id:Int) = viewModelScope.launch {
        _getOrderState.value = UiStateList.LOADING
        try {
            val response = repository.getAllOrders(id)
            if (response.success){
                _getOrderState.value = UiStateList.SUCCESS(response.data)
            }else{
                _getOrderState.value = UiStateList.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _getOrderState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}