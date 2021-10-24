package com.example.lab2_3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun makeColored(view: View)
    {
        when (view.id) {
            R.id.box_one_text   -> view.setBackgroundResource(R.drawable.test)
            R.id.box_two_text   -> view.setBackgroundResource(R.drawable.bomb)
            R.id.box_three_text -> view.setBackgroundResource(R.drawable.numero1)
            R.id.box_four_text  -> view.setBackgroundResource(R.drawable.numero2)
            R.id.box_five_text  -> view.setBackgroundResource(R.drawable.numero3)
            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun setListeners()
    {
        val boxOneText = findViewById<TextView>(R.id.box_one_text)
        val boxTwoText = findViewById<TextView>(R.id.box_two_text)
        val boxThreeText = findViewById<TextView>(R.id.box_three_text)
        val boxFourText = findViewById<TextView>(R.id.box_four_text)
        val boxFiveText = findViewById<TextView>(R.id.box_five_text)

        val rootConstraintLayout = findViewById<View>(R.id.constraint_layout)

        val clickableViews: List<View> =
            listOf(boxOneText, boxTwoText, boxThreeText,
                boxFourText, boxFiveText, rootConstraintLayout)
        for(i in clickableViews)
            i.setOnClickListener{makeColored(i)}

    }
}