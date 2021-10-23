package com.example.lab2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            addNickname(it)
        }
    }

    private fun addNickname(view: View) {
        val editText = findViewById<EditText>(R.id.name_edit)
        val textView = findViewById<TextView>(R.id.name_text)
        textView.text = editText.text
        editText.visibility = View.GONE
        view.visibility = View.GONE
        textView.visibility = View.VISIBLE

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}