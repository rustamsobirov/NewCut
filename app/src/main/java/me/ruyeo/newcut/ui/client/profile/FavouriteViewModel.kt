package me.ruyeo.newcut.ui.client.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.newcut.data.model.Barbershop
import me.ruyeo.newcut.repository.MainRepository
import me.ruyeo.newcut.utils.Constants.ERROR_MESSAGE
import me.ruyeo.newcut.utils.UiStateList
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _getFavouritesState =
        MutableStateFlow<UiStateList<Barbershop>>(UiStateList.EMPTY)
    val getFavouritesState = _getFavouritesState

    fun getFavourites(id: Int) = viewModelScope.launch {
        _getFavouritesState.value = UiStateList.LOADING
        try {
            val response = repository.getFavourites(id)
            if (response.success){
                _getFavouritesState.value = UiStateList.SUCCESS(response.data.barberShops)
            }else{
                _getFavouritesState.value = UiStateList.ERROR(response.error.message)
            }
        }catch (e: Exception){
            _getFavouritesState.value = UiStateList.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}