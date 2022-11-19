package com.compass.compass___app

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import com.compass.compass___app.databinding.ActivityMainBinding
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity(),SensorEventListener {
    private var sensorManage: SensorManager? = null

    private var degreeStart = 0f

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sensorManage = getSystemService(SENSOR_SERVICE) as SensorManager?
    }

    override fun onPause() {
        super.onPause()
        sensorManage?.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        sensorManage?.registerListener(
            this, sensorManage?.getDefaultSensor(Sensor.TYPE_ORIENTATION),
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = event?.values?.get(0)?.roundToInt()?.toFloat()
        binding.DegreeTV.text = resources.getString(R.string.heading,degree.toString())
        val ra = RotateAnimation(
            degreeStart,
            -degree!!,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        ra.fillAfter = true
        ra.duration = 210
        binding.compassImage.startAnimation(ra)
        degreeStart = -degree
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}