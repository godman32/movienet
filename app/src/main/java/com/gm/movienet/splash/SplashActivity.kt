package com.gm.movienet.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gm.movienet.HomeActivity
import com.gm.movienet.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by @godman on 04/07/23.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        lifecycleScope.launch {
            delay(1700L)
            navigateToNextScreen()
        }
    }

    private fun navigateToNextScreen() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }
}