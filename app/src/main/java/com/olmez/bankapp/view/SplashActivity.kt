package com.olmez.bankapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.olmez.bankapp.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)




        /**
         * Splash Screen ekranından geçiş yaparken gecikme sağlanması için GlobalScope oluşturulmuştur
         */
        GlobalScope.launch {

            delay(2000)
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java) // Splash ekran sonrası yönlendirilecek aktiviteyi belirleyin.
            startActivity(mainIntent)
            finish()

        }

    }
}