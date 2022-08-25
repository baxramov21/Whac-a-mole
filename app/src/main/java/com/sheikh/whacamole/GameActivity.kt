package com.sheikh.whacamole

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel
    private var currentScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        score.text = "0"
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        recyclerView = holesRV
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val adapter = HolesAdapter()
        val imageIDList = mutableListOf<Int>()
        for (i in 0 until HOLES_COUNT) {
            imageIDList.add(R.drawable.grass)
        }
        adapter.holeImageIDList = imageIDList
        recyclerView.adapter = adapter

        var randomizedImageView: ImageView? = null
        viewModel.time.observe(this) {
            // Show how much time left
            val timePattern = getString(R.string.time)
            val leftSecs = it.toString()
            timer.text = String.format(timePattern, leftSecs)

            // Show mole
            randomizedImageView = showMole()
        }

        onHoleClick(adapter)
        // Start timer
        startTimer()
    }

    override fun onPause() {
        viewModel.stopTimer()
        super.onPause()
    }

    // Change score when mole was clicked
    private fun onHoleClick(adapter: HolesAdapter) {
        adapter.holeClickListener = object : HolesAdapter.CoinClickListener {
            override fun onHoleClick(position: Int) {
                val imageViewClickedHole = recyclerView[position] as ImageView
                if (compareDrawables(imageViewClickedHole)) {
                    currentScore++
                    val scorePattern = getString(R.string.score)
                    score.text = String.format(scorePattern, currentScore)
                    imageViewClickedHole.setImageResource(R.drawable.whack)
                    imageViewClickedHole.shakeView()
                }
            }
        }
    }

    private fun compareDrawables(imageView: ImageView): Boolean {
        return imageView.drawable.constantState ==
                ContextCompat.getDrawable(this, R.drawable.mole)?.constantState
    }

    // Shake view when mole was clicked
    private fun View.shakeView() {
        ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 30F, 0F, 30F).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 800
            start()
        }
    }

    // Get Randomized hole position
    private fun getRandomHole(): Int {
        val maxRandom = HOLES_COUNT - 1
        return (0..maxRandom).random()
    }

    private fun showMole(): ImageView {
        // Show mole
        val randomHole = recyclerView[getRandomHole()] as ImageView
        randomHole.setImageResource(R.drawable.mole)
        val milliSecs = 700
        Handler().postDelayed({
            randomHole.setImageResource(R.drawable.grass)
        }, milliSecs.toLong())
        return randomHole
    }

    private fun startTimer() {
        viewModel.startTime(TIME_IN_SECONDS) {
            openScoreActivity()
        }
    }

    private fun openScoreActivity() {
        val intentToScoreActivity = Intent(GameActivity@ this, ScoreActivity::class.java)
        intentToScoreActivity.putExtra("record", currentScore)
        startActivity(intentToScoreActivity)
    }

    companion object {
        const val TIME_IN_SECONDS = 20
        private const val HOLES_COUNT = 9
    }
}