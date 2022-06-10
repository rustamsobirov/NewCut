package me.ruyeo.newcut.ui.client.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Notification
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    private val _notificationState = MutableStateFlow<UiStateList<Notification>>(UiStateList.EMPTY)
    var notificationState = _notificationState

    fun getNotification() = viewModelScope.launch {
        _notificationState.value = UiStateList.LOADING
        try {
            val response = repository.getNotifications()
            if (response.success){
                _notificationState.value = UiStateList.SUCCESS(response.data)
            }else{
                _notificationState.value = UiStateList.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _notificationState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}