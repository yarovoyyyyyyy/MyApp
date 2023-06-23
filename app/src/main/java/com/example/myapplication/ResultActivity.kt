package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView: TextView = findViewById(R.id.result_text_view)
        val correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0)
        resultTextView.text = "Правельных ответов: $correctAnswers"
        val repeatButton: Button = findViewById(R.id.repeat_button)
        repeatButton.setOnClickListener {
            finish()
        }
    }
}