package com.example.lab09_broadcastreceiver_kotlin

import androidx.appcompat.app.AppCompatActivity

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var flag: Boolean = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val b = intent.extras
            tv_clock.setText(
                String.format(
                    "%02d:%02d:%02d",
                    b!!.getInt("H"),
                    b.getInt("M"),
                    b.getInt("S")
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(receiver, IntentFilter("MyMessage"))
        flag = MyService.flag!!
        if(flag)btn_start.setText("暫停")
        else btn_start.setText("開始")

        btn_start.setOnClickListener {
            flag=!flag
            if(flag){
                btn_start.setText("暫停")
                Toast.makeText(this,"計時開始",Toast.LENGTH_SHORT).show()
            }
            else{
                btn_start.setText("開始")
                Toast.makeText(this,"計時暫停",Toast.LENGTH_SHORT).show()
            }
            startService(Intent(this,MyService::class.java).putExtra("flag",flag))

        }

         fun onDestroy() {
            super.onDestroy()
            unregisterReceiver(receiver)
        }
    }
}
