package me.ruyeo.newcut.ui.client.appointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppointmentSharedViewModel: ViewModel() {

    val mapClick = MutableLiveData<Boolean>()

    fun setValue(mapClick : Boolean){
        this.mapClick.value = mapClick
    }

    fun getValue() = mapClick

}