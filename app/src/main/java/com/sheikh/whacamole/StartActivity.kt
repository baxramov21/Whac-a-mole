package com.sheikh.whacamole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val record = viewModel.getRecord()
        bestScoreValue.text =  "$record"
    }

    fun onClickStartGame(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}