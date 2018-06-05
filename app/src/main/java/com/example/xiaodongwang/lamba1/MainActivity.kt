package com.example.xiaodongwang.lamba1

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    inner class StartService : View.OnClickListener {
        override fun onClick(v: View?) {
            if (payload == null || payload.text.isNullOrBlank()) {
                Log.e(LOG_TAG, "empty payload")
                Toast.makeText(
                        this@MainActivity, "payload is empty", Toast.LENGTH_SHORT
                ).show()
                return
            }
            val intent = Intent("com.example.xiaodongwang.lamba1")
            intent.setPackage("com.example.xiaodongwang.lamba1")
            var uuid = UUID.randomUUID().toString()
            intent.putExtra("name", uuid)
            intent.putExtra("payload", payload.text.toString())
            Log.i(LOG_TAG, "start service")
            if (startForegroundService(intent) == null) {
                Log.e(LOG_TAG, "failed to start service")
            }
        }
    }

    inner class StopService : View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent("com.example.xiaodongwang.lamba1")
            intent.setPackage("com.example.xiaodongwang.lamba1")
            Log.i(LOG_TAG, "stop service")
            if(!stopService(intent)) {
                Log.e(LOG_TAG, "failed to stop service")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(LOG_TAG, "create activity")
        requestPermissions()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start_service.setOnClickListener(StartService())
        stop_service.setOnClickListener(StopService())
    }

    fun requestPermissions() {
        var permissions = ArrayList<String>()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            for (permission in arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET
            )) {
                Log.d("permission", "permission denied to $permission - requesting it")
                permissions.add(permission)

            }
            requestPermissions(permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
        private val LOG_TAG = "MainActivity"
        private val PERMISSION_REQUEST_CODE = 1
    }
}
