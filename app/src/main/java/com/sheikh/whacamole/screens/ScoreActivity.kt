package com.sheikh.whacamole.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.sheikh.whacamole.R
import com.sheikh.whacamole.view_model.MainViewModel
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_start.*

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val highestScore = viewModel.getRecord()
        val intent = intent
        val record = intent.getIntExtra("record", 0)
        var scoreText = "$record"
        var bestScoreText = "$highestScore"

        if (record > highestScore) {
            viewModel.setRecord(record)
            scoreText = "New record: $record"
            bestScoreText = "$record"
        }
        score.text = scoreText
        bestScore.text = bestScoreText
    }

    fun onClickRestartGame(view: View) {
        startActivity(Intent(this,GameActivity::class.java))
    }
}