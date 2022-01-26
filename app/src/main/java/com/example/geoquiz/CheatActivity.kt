package com.example.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.geoquiz.databinding.ActivityCheatBinding

private lateinit var binding: ActivityCheatBinding

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var answerIsTrue = intent.getBooleanExtra("answer", false)

        with(binding) {
            showAnswerButton.setOnClickListener {
                val answerText = when {
                    answerIsTrue -> "TRUE"
                    else -> "FALSE"
                }
                answerTextView.text = answerText
                setAnswerShownResult()
            }
        }
    }

    private fun setAnswerShownResult() {
        val intent = Intent()
        intent.putExtra("isCheater", true)
        setResult(RESULT_OK, intent)
    }
}
