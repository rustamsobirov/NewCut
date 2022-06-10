package me.ruyeo.newcut.ui.client.detail.detail_in

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.ruyeo.newcut.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class DetailAboutViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel(){


}