package me.ruyeo.newcut.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.BaseResponseObject
import me.ruyeo.newcut.data.model.Login
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _login =
        MutableStateFlow<UiStateObject<BaseResponseObject<Login>>>(UiStateObject.EMPTY)
    val login = _login

    fun login(phoneNumber: String) = viewModelScope.launch {
        _login.value = UiStateObject.LOADING
        try {
            val response = repository.login(phoneNumber)
            _login.value = UiStateObject.SUCCESS(response)

        } catch (e: Exception) {
            _login.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }

    fun reset(){
        _login.value = UiStateObject.EMPTY
    }
}