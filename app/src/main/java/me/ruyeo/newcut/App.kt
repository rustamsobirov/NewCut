package me.ruyeo.newcut

import android.app.Application
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
         localeManager = LocaleManager(this)
        localeManager!!.setLocale(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager!!.setLocale(this)

    }
    companion object {
        lateinit var instance: App
            private set
        var localeManager:LocaleManager? = null

    }

}