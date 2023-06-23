package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.myapplication.R

class QuizViewModel : ViewModel() {
    private val TAG = "QuizViewModel"

    var currentIndex = 0
    private val questionBank = listOf(
        Question(R.string.question_oceans, answer = true, userAnswer = false, correctAnswer = false),
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
            correctAnswer = false
        ),
        Question(R.string.question_asia, answer = true, userAnswer = false, correctAnswer = false)
    )
    val currentQuestionAnswer: Boolean
        get() =
            questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() =
            questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) %
                questionBank.size
    }

}