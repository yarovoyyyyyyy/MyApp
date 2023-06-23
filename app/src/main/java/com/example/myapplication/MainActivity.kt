package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Question
import com.example.myapplication.QuizViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView
    private val TAG = "MainActivity"

    private var currentIndex = 0

    private val questionBank = listOf(
        Question(R.string.question_oceans, answer = true, userAnswer = false, correctAnswer = true),
        Question(R.string.question_mideast,
            answer = false,
            userAnswer = false,
            correctAnswer = false
        ),
        Question(R.string.question_africa,
            answer = false,
            userAnswer = false,
            correctAnswer = false
        ),
        Question(R.string.question_americas,
            answer = true,
            userAnswer = false,
            correctAnswer = true
        ),
        Question(R.string.question_asia, answer = true, userAnswer = false, correctAnswer = true)
    )

    private val quizViewModel: QuizViewModel by lazy {

        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        if (currentIndex == 0) {
            prevButton.visibility = View.INVISIBLE
        } else {
            prevButton.visibility = View.VISIBLE
        }
        trueButton.isEnabled = true
        falseButton.isEnabled = true
        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }

    companion object {
        private const val KEY_CURRENT_INDEX = "current_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previes_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            currentIndex = quizViewModel.currentIndex
            updateQuestion()

            if (currentIndex == questionBank.size - 1) {
                val correctAnswers = calculateCorrectAnswers()
                val totalQuestions = questionBank.size

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("CORRECT_ANSWERS", correctAnswers)
                intent.putExtra("TOTAL_QUESTIONS", totalQuestions)
                startActivity(intent)
            }
        }

        prevButton.setOnClickListener {
            currentIndex = (currentIndex - 1 + questionBank.size) % questionBank.size
            updateQuestion()

            if (currentIndex == 0) {
                prevButton.visibility = View.INVISIBLE
            } else {
                prevButton.visibility = View.VISIBLE
            }

            nextButton.isEnabled = true
            nextButton.visibility = View.VISIBLE
        }

        quizViewModel.currentIndex = currentIndex

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX)
            quizViewModel.currentIndex = currentIndex
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_INDEX, currentIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX)
        quizViewModel.currentIndex = currentIndex
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,
            "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val question = questionBank[currentIndex]
        question.userAnswer = userAnswer

        val correctAnswer = question.answer

        trueButton.isEnabled = false
        falseButton.isEnabled = false
        trueButton.visibility = View.INVISIBLE
        falseButton.visibility = View.INVISIBLE

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (currentIndex < questionBank.size - 1) {
            currentIndex++
            updateQuestion()
        } else {
            val correctAnswers = calculateCorrectAnswers()
            val totalQuestions = questionBank.size

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("CORRECT_ANSWERS", correctAnswers)
            intent.putExtra("TOTAL_QUESTIONS", totalQuestions)
            startActivity(intent)
        }
    }

    private fun calculateCorrectAnswers(): Int {
        var correctCount: Int = 0
        for (question in questionBank) {
            if (question.userAnswer == question.correctAnswer) {
                correctCount += 1
            }
        }
        return correctCount
    }
}