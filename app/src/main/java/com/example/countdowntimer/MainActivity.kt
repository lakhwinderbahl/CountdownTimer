package com.example.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
class MainActivity : AppCompatActivity() {

    lateinit var timer: Chronometer
    var running = false
    var offset: Long = 0

    val offsetkey = "offset"
    val runningkey = "running"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timer = findViewById<Chronometer>(R.id.Chrono)

        // restore saved instance from bundle
        if(savedInstanceState != null) {
            offset = savedInstanceState.getLong(offsetkey)
            running = savedInstanceState.getBoolean(runningkey)

            setTimer(0)
            if(running) {
                timer.start()
            }
        }

        val less_5Sec = findViewById<Button>(R.id.btnless5sec)
        less_5Sec.setOnClickListener {
            Log.v("btn click", "less 5 sec")
            setTimer(5000)
        }
        val add_5Sec = findViewById<Button>(R.id.btnadd5sec)
        add_5Sec.setOnClickListener {
            Log.v("btn click", "add 5 sec")
            setTimer(-5000)
        }
        val startBtn = findViewById<Button>(R.id.btnstart)
        startBtn.setOnClickListener {
            println("Start!")

            if(!running) {
                setTimer(0)
                timer.start()
                running = true
            }
        }
        val stopBtn = findViewById<Button>(R.id.btnstop)
        stopBtn.setOnClickListener {
            println("Stop")

            if(running) {
                saveOffset()
                timer.stop()
                running = false
            }
        }
        val resetBtn = findViewById<Button>(R.id.btnreset)
        resetBtn.setOnClickListener {
            println("Reset")

            resetTimer()
            running = false
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        if(running) {
            saveOffset()
        }
        savedInstanceState.putLong(offsetkey, offset)
        savedInstanceState.putBoolean(runningkey, running)

        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onStop() {
        super.onStop()

        if(running) {
            saveOffset()
        }
    }

    private fun setTimer(value: Long) {
        offset += value
        timer.base = SystemClock.elapsedRealtime() - offset
    }

    private fun resetTimer() {
        timer.base = SystemClock.elapsedRealtime()
        offset = 0
    }

    private fun saveOffset() {
        offset = timer.base - SystemClock.elapsedRealtime()
    }

}
