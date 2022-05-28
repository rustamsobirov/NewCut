package me.ruyeo.newcut.ui.client

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import me.ruyeo.newcut.R
import me.ruyeo.newcut.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseAnalytics.getInstance(this)
        binding.apply {
            bnvMain.itemIconTintList = null
            navController = findNavController(R.id.nav_host_main)
            bnvMain.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.mapFragment -> {
                        hideStatusBarAndBottomBar()
                        bnvMain.isVisible = true
                    }
                    R.id.detailFragment -> {
                        hideStatusBarAndBottomBar()
                        bnvMain.isVisible = false
                    }
                    R.id.editProfileFragment -> {
                        bnvMain.isVisible = false
                    }
                    else -> {
                        showStatusBarAndBottomBar()
                        bnvMain.isVisible = true
                    }
                }
            }
        }
    }

    fun hideStatusBarAndBottomBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTheme(R.style.homeTheme)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun showStatusBarAndBottomBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setTheme(R.style.Theme_NewCut)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}