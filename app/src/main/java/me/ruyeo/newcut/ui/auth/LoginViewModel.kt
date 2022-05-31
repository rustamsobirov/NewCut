package me.ruyeo.newcut.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

   // private val _login = MutableStateFlow<UiStateObject<>>
}