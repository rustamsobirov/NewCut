package me.ruyeo.newcut.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.data.model.LoginResponse
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _confirmationLogin = MutableStateFlow<UiStateObject<LoginResponse>>(UiStateObject.EMPTY)
    val confirmLogin = _confirmationLogin

    fun confirmationLogin(map: HashMap<String,Any>) = viewModelScope.launch {
        _confirmationLogin.value = UiStateObject.LOADING
        try {
            val response = repository.confirmationLoginCode(map)
            if (response.success){
                _confirmationLogin.value = UiStateObject.SUCCESS(response)
            }else{
                _confirmationLogin.value = UiStateObject.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _confirmationLogin.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }

    private val _confirmationRegister = MutableStateFlow<UiStateObject<LoginResponse>>(UiStateObject.EMPTY)
    val confirmRegister = _confirmationLogin

    fun confirmationRegister(map: HashMap<String,Any>) = viewModelScope.launch {
        _confirmationRegister.value = UiStateObject.LOADING
        try {
            val response = repository.confirmationRegisterCode(map)
            if (response.success){
                _confirmationRegister.value = UiStateObject.SUCCESS(response)
            }else{
                _confirmationRegister.value = UiStateObject.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _confirmationRegister.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}