package me.ruyeo.newcut.ui.client

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
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

        binding.apply {
            bnvMain.itemIconTintList = null
            navController = findNavController(R.id.nav_host_main)
            bnvMain.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.detailFragment -> {
                        bnvMain.visibility = View.GONE
                    }
                    R.id.mapViewFragment -> {
                        bnvMain.visibility = View.GONE
                    }
                    else -> {
                        bnvMain.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}