package com.example.timerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var productiveSeconds = 0
    private var nonProductiveSeconds = 0
    private var sleepSeconds = 0

    private var productiveRunning = false
    private var nonProductiveRunning = false
    private var sleepRunning = false

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())

        val tvProductive = findViewById<TextView>(R.id.tvProductive)
        val tvNonProductive = findViewById<TextView>(R.id.tvNonProductive)
        val tvSleep = findViewById<TextView>(R.id.tvSleep)

        val btnProductive = findViewById<Button>(R.id.btnProductive)
        val btnNonProductive = findViewById<Button>(R.id.btnNonProductive)
        val btnSleep = findViewById<Button>(R.id.btnSleep)
        val btnReset = findViewById<Button>(R.id.btnReset)

        btnProductive.setOnClickListener {
            productiveRunning = !productiveRunning
            if (productiveRunning) handler.post(productiveRunnable(tvProductive))
        }

        btnNonProductive.setOnClickListener {
            nonProductiveRunning = !nonProductiveRunning
            if (nonProductiveRunning) handler.post(nonProductiveRunnable(tvNonProductive))
        }

        btnSleep.setOnClickListener {
            sleepRunning = !sleepRunning
            if (sleepRunning) handler.post(sleepRunnable(tvSleep))
        }

        btnReset.setOnClickListener {
            productiveSeconds = 0
            nonProductiveSeconds = 0
            sleepSeconds = 0
            tvProductive.text = "00:00:00"
            tvNonProductive.text = "00:00:00"
            tvSleep.text = "00:00:00"
            productiveRunning = false
            nonProductiveRunning = false
            sleepRunning = false
        }
    }

    private fun productiveRunnable(tv: TextView): Runnable = object : Runnable {
        override fun run() {
            if (productiveRunning) {
                productiveSeconds++
                tv.text = formatTime(productiveSeconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun nonProductiveRunnable(tv: TextView): Runnable = object : Runnable {
        override fun run() {
            if (nonProductiveRunning) {
                nonProductiveSeconds++
                tv.text = formatTime(nonProductiveSeconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun sleepRunnable(tv: TextView): Runnable = object : Runnable {
        override fun run() {
            if (sleepRunning) {
                sleepSeconds++
                tv.text = formatTime(sleepSeconds)
                handler.postDelayed(this, 1000)
            }
        }
    }

    private fun formatTime(seconds: Int): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        return String.format("%02d:%02d:%02d", h, m, s)
    }
}
