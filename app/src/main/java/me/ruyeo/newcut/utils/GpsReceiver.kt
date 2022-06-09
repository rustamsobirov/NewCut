package me.ruyeo.newcut.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GpsReceiver(private val onLocationChange: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action?.matches(Regex("android.location.PROVIDERS_CHANGED")) == true) {
            onLocationChange()
        }
    }
}