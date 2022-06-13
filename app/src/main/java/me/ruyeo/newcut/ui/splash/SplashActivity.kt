package me.ruyeo.newcut.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import me.ruyeo.newcut.SharedPref
import me.ruyeo.newcut.databinding.ActivitySplashBinding
import me.ruyeo.newcut.ui.auth.LoginActivity
import me.ruyeo.newcut.ui.client.MainActivity
import javax.inject.Inject

/**
 * In SplashActivity, user can visit SignInActivity or MainActivity
 */

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding? = null
    private var job: Job? = null

    @Inject
    lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        job = lifecycleScope.launchWhenStarted {
            delay(2000)
            if (sharedPref.token.isNotEmpty()) {
                Intent(this@SplashActivity, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        job?.cancel()
    }
}
