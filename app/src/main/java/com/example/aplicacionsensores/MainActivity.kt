package com.example.aplicacionsensores

import com.example.BaseDeDatos
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import androidx.room.RoomDatabase
import entidades.Acelerometro

class MainActivity : AppCompatActivity(), SensorEventListener {

    val LIGHT_VIEW_ID : Int = R.id.vLight
    val ACC_VIEW_ID : Int = R.id.vAcc
    val gravity = FloatArray(3) { i -> 0.0f }
    val accNoGravity = FloatArray(3)
    var samples = 0
    var measured_samples = 0

    private lateinit var sensorManager: SensorManager
    private var mLight: Sensor? = null
    private var mAcc: Sensor? = null

    private var current = 0L
    private val BATCH_SIZE = 10
    private var batch = ArrayList<Acelerometro>(BATCH_SIZE)

    // Create database.
    // Databse contains table Accelerometer, with columns x,y,z.
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        mAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        val sensorname : String = event.sensor.name
        when(sensorname) {
            "ACCELEROMETER" -> {
                val acc = event.values
                // alpha = t / (t + dT)
                // alpha = 0.8 -> 1 / 1.25
                // alpha = 1 / (1 + 0.25)
                samples++
                if(System.currentTimeMillis() - current > 1000) {
                    val alpha : Float = 0.8f
                    gravity[0] = alpha * gravity[0] + (1 - alpha) * acc[0]
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * acc[1]
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * acc[2]

                    accNoGravity[0]  = acc[0] - gravity[0]
                    accNoGravity[1]  = acc[1] - gravity[1]
                    accNoGravity[2]  = acc[2] - gravity[2]

                    updateValue("ACCGRAV", acc, ACC_VIEW_ID)
                    updateValue("ACCNOGRAV", accNoGravity, LIGHT_VIEW_ID)

                    batch.add(Acelerometro(accNoGravity[0], accNoGravity[1], accNoGravity[2]))

                    if(measured_samples == 10) {
                        val acc_dao = getDatabase().acelerometroDao()
                        acc_dao.insertAll(batch)
                        batch = ArrayList<Acelerometro>(10)
                        measured_samples = 0
                    }

                    Toast.makeText(this, "Samples taken: " + samples, Toast.LENGTH_SHORT).show()
                    samples = 0
                    measured_samples++
                    current = System.currentTimeMillis()
                }
            }
        }
    }

    private fun updateValue(sensorname: String, arg: FloatArray, viewId: Int) {
        val view: TextView = findViewById(viewId)
        view.text = sensorname + ": x=" + arg[0].toString() + ", y=" + arg[1].toString() + ", z=" + arg[2].toString()
    }

    override fun onResume() {
        super.onResume()
        // mLight? <- ? <- is a safe call
        // SENSOR_DELAY = SAMPLE FREQUENCY?
       mAcc?.also { acc ->
           sensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_FASTEST)
       }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    public fun getDatabase(): BaseDeDatos {
        val db = Room.databaseBuilder(
            applicationContext,
            BaseDeDatos::class.java, "DatosAcelerometro"
        ).build()
        return db
    }
}