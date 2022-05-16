package com.example.monitoriacarregamento

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var caboReceiver: CaboReceiver? = null
    private lateinit var ifCabo: IntentFilter
    private lateinit var tvReceiver: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.tvReceiver = findViewById(R.id.tvReceiver)
    }

    override fun onResume() {
        super.onResume()

        if (this.caboReceiver == null){
            this.caboReceiver = CaboReceiver()
            this.ifCabo = IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
        }
        registerReceiver(this.caboReceiver, this.ifCabo)
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(this.caboReceiver)
    }

    inner class CaboReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)){
                this@MainActivity.tvReceiver.text = "Conectou!"
                Toast.makeText(context, "Conectou!, Carregando...", Toast.LENGTH_SHORT).show()
            }else{
                this@MainActivity.tvReceiver.text = "Desconectou!"
                Toast.makeText(context, "Desconectou!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}