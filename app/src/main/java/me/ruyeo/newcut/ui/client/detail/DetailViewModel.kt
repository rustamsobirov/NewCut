package me.ruyeo.newcut.ui.client.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _barbershopState = MutableStateFlow<UiStateObject<Barbershop>>(UiStateObject.EMPTY)
    var barbershopState = _barbershopState

    fun getBarbershopById(id: Int) = viewModelScope.launch {
        _barbershopState.value = UiStateObject.LOADING
        try {
            val response = repository.getBarbershopById(id)
            if (response.success){
                _barbershopState.value = UiStateObject.SUCCESS(response.data)
            }else{
                _barbershopState.value = UiStateObject.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _barbershopState.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}