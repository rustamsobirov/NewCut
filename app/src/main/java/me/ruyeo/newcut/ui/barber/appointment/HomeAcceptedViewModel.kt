package me.ruyeo.newcut.ui.barber.appointment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class HomeAcceptedViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

  /*  private val _updateOrganization = MutableStateFlow<UiStateObject<ResponseOrganization>>(UiStateObject.EMPTY)
    var updateOrganization = _updateOrganization
    fun updateUserOrganization(organization: Organization) = viewModelScope.launch {
        _updateOrganization.value = UiStateObject.LOADING
        try {
            val response = repository.upDateOrganization(organization)
            if (response.success) {
                _updateOrganization.value = UiStateObject.SUCCESS(response.data)
            } else {
                _updateOrganization.value = UiStateObject.ERROR(response.error.message)
            }

        } catch (e: Exception) {
            _updateOrganization.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
*/

}