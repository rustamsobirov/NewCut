package me.ruyeo.newcut.ui.client.appointment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.BarberPassedOrder
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.UiStateList
import javax.inject.Inject
@HiltViewModel
class PassViewModel  @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _getPassedOrder = MutableStateFlow<UiStateList<BarberPassedOrder>>(UiStateList.EMPTY)
    val getPassedOrder = _getPassedOrder

    fun getPassedOrders(id:Int) = viewModelScope.launch {
        _getPassedOrder.value = UiStateList.LOADING
        try {
            val response = repository.getPassedOrders(id)
            if (response.success){
                _getPassedOrder.value = UiStateList.SUCCESS(response.data)
            }else{
                _getPassedOrder.value = UiStateList.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _getPassedOrder.value = UiStateList.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}