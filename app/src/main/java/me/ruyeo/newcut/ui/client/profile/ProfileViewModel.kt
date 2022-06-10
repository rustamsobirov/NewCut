package me.ruyeo.newcut.ui.client.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.AboutMe
import me.ruyeo.newcut.data.model.User
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.UiStateList
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _mainProfile = MutableStateFlow<UiStateObject<User>>(UiStateObject.EMPTY)
    var mainProfile = _mainProfile
    fun getAboutMe(id: Int) = viewModelScope.launch {
        _mainProfile.value = UiStateObject.LOADING
        try {
            val response = repository.getAbout(id)
            if (response.success) {
                _mainProfile.value = UiStateObject.SUCCESS(response.data)
            } else {
                _mainProfile.value = UiStateObject.ERROR(response.error.message)
            }

        } catch (e: Exception) {
            _mainProfile.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }

}