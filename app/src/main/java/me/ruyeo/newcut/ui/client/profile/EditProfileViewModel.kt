package me.ruyeo.newcut.ui.client.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.User
import me.ruyeo.newcut.data.model.UserUpdate
import me.ruyeo.newcut.repository.AuthRepository
import me.ruyeo.newcut.utils.Constants
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _updateProfile = MutableStateFlow<UiStateObject<User>>(UiStateObject.EMPTY)
    var updateProfile = _updateProfile
    fun updateUserProfile(userUpdate: UserUpdate) = viewModelScope.launch {
        _updateProfile.value = UiStateObject.LOADING
        try {
            val response = repository.updateUser(userUpdate)
            if (response.success) {
                _updateProfile.value = UiStateObject.SUCCESS(response.data)
            } else {
                _updateProfile.value = UiStateObject.ERROR(response.error.message)
            }

        } catch (e: Exception) {
            _updateProfile.value =
                UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}