package com.android.saludable

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        startTimer()
        finish()
    }

    private fun startTimer(){
        object: CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                //TODO("Not yet implemented")
            }

            override fun onFinish() {
                val intent = Intent(applicationContext, AuthActivity::class.java).apply{}
                startActivity(intent)
            }
        }.start()
    }


}