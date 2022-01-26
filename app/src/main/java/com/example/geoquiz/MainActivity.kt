package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.geoquiz.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(
        Question(R.string.question_1, false),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, true),
        Question(R.string.question_6, true),
        Question(R.string.question_7, false),
        Question(R.string.question_8, true),
        Question(R.string.question_9, false),
        Question(R.string.question_10, true)
    )

    private var currentIndex = 0
    var isCheater = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.getInt("currentIndex")?.let {
            currentIndex = it
        }

        with(binding) {
            trueButton.setOnClickListener {
                checkAnswer(true)
            }

            falseButton.setOnClickListener {
                checkAnswer(false)
            }

            nextButton.setOnClickListener {
                currentIndex = (currentIndex + 1) % questionBank.size
                updateQuestion()
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            }

            previousButton.setOnClickListener {
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            }

            cheatButton.setOnClickListener {
                val answerIsTrue = questionBank[currentIndex].answer
                val answer = Intent(this@MainActivity, CheatActivity::class.java)
                answer.putExtra("answer", answerIsTrue)
                startActivityForResult(answer, 0)
            }
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        binding.nextButton.isEnabled = currentIndex != 9
        binding.previousButton.isEnabled = currentIndex != 0
        isCheater=false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = when {
            isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 0) {
            isCheater = data?.getBooleanExtra("isCheater", false) ?: false
        }
    }
        override fun onSaveInstanceState(outState: Bundle) {
            outState.putInt("currentIndex", currentIndex)
            super.onSaveInstanceState(outState)
        }
    }
