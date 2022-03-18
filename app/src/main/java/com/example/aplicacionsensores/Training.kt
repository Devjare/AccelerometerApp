package com.example.aplicacionsensores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import java.util.*

class Training : AppCompatActivity() {
    private  var SEC = "Seconds"
    private  var RUNNING = "Running"
    private  var WAS_RUNNING = "Was Running"

    private var seconds : Int = 0
    private var running : Boolean = true
    private var wasRunning : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt(SEC)
            running = savedInstanceState.getBoolean(RUNNING)
            wasRunning = savedInstanceState.getBoolean(WAS_RUNNING)
        }
        runTimer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SEC, seconds)
        outState.putBoolean(RUNNING, running)
        outState.putBoolean(WAS_RUNNING, wasRunning)
    }

    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    override fun onResume() {
        super.onResume()
        if(wasRunning) {
            running = true
        }
    }

    fun onClickStart(view: View) {
        running = true
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view : View) {
        running = false
        seconds = 0
    }

    private fun runTimer() {
        val view: TextView = findViewById(R.id.tvTimer)
        val handler: Handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                var hours : Int = seconds / 3600
                var minutes: Int = (seconds % 3600) / 60
                var secs : Int = seconds % 60

                var time : String = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours,
                    minutes, secs)

                view.setText(time)

                if(running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })

    }
}