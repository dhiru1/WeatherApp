package com.example.weatherapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var editText:EditText
    lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText=findViewById(R.id.editTextCity)
        button=findViewById(R.id.buttonGet)

        button.setOnClickListener {
            var intent: Intent =Intent(this,GetWeather::class.java)
            intent.putExtra("city",editText.text.toString())
            startActivity(intent)
        }

    }
}