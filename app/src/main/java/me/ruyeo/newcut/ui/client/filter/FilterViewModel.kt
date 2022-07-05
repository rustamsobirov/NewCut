package me.ruyeo.newcut.ui.client.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.model.filter.Service
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val repository: MainRepository):ViewModel() {
    private val _getServiceState =
        MutableStateFlow<UiStateList<Service>>(UiStateList.EMPTY)
    val getServiceState = _getServiceState

    fun getAllServices() = viewModelScope.launch {
        _getServiceState.value  = UiStateList.LOADING

        try {
            val responce = repository.getAllServices()
            if (responce.success){
                _getServiceState.value = UiStateList.SUCCESS(responce.data)
            }else{
                _getServiceState.value = UiStateList.ERROR(responce.error.message)
            }
        }catch (e:Exception){
            _getServiceState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}