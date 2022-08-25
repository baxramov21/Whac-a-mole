package com.sheikh.whacamole

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom)
        imageViewAppLogo.startAnimation(animation)
        val milliSecs = 2000
        Handler().postDelayed({
            startActivity(
                Intent(
                    this@MainActivity,
                    StartActivity::class.java
                )
            )
        }, milliSecs.toLong())
    }
}