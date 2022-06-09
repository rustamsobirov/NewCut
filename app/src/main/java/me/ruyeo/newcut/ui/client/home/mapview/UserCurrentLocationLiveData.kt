package me.ruyeo.newcut.ui.client.home.mapview

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng

class UserCurrentLocationLiveData(private val context: Context, value: LatLng?) :
    LiveData<LatLng>(value) {
    override fun onActive() {
        super.onActive()
    }
}