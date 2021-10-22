package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.NumberFormatException

abstract class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val rollButton: Button = findViewById(R.id.roll_button)
//        rollButton.setOnClickListener{ rollDice() }
//
//        val incrementButton: Button = findViewById(R.id.plus_one)
//        incrementButton.setOnClickListener { increment() }
    }

//    private fun rollDice(){
//        val resultText: TextView = findViewById(R.id.roll_text)
//        val randomInt = (1..6).random()
//        resultText.text = randomInt.toString()
//    }
//
//    private fun increment(){
//        val resultText: TextView = findViewById(R.id.roll_text)
//        val number = try{resultText.text.toString().toInt()} catch (e : NumberFormatException) { 0 }
//        if (number < 6)
//            resultText.text = (number + 1).toString()
//    }
}
