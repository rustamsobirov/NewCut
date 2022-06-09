package me.ruyeo.newcut.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Confirm
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _confirmationCode = MutableStateFlow<UiStateObject<Confirm>>(UiStateObject.EMPTY)
    val confirmCode = _confirmationCode

    fun confirmationCode(map: HashMap<String,Any>) = viewModelScope.launch {
        _confirmationCode.value = UiStateObject.LOADING
        try {
            val response = repository.confirmationCode(map)
            if (response.success){
                _confirmationCode.value = UiStateObject.SUCCESS(response.data)
            }else{
                _confirmationCode.value = UiStateObject.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _confirmationCode.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}