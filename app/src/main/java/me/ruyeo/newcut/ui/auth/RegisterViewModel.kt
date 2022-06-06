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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel(){

    private val _register = MutableStateFlow<UiStateObject<LoginResponse>>(UiStateObject.EMPTY)
    val register = _register

    fun register(phoneNumber: Login) = viewModelScope.launch {
        _register.value = UiStateObject.LOADING
        try {
            val response = repository.register(phoneNumber)
            if (response.success){
                _register.value = UiStateObject.SUCCESS(response)
            }else{
                _register.value = UiStateObject.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _register.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}