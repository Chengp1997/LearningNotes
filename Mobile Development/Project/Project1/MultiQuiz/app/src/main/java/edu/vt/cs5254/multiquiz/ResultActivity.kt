package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.vt.cs5254.multiquiz.databinding.ActivityResultBinding


private const val EXTRA_CORRECT_ANSWERS ="edu.vt.cs5254.multiquiz.correct_answers"
private const val EXTRA_TOTAL_QUESTIONS="edu.vt.cs5254.multiquiz.total_questions"
private const val EXTRA_HINTS_USED="edu.vt.cs5254.multiquiz.hints_used"
const val EXTRA_RESET_ALL="edu.vt.cs5254.multiquiz.reset_all"

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding
    private var correctAnswers = 0
    private var totalQuestions = 0
    private var hintsUsed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        correctAnswers = intent.getIntExtra(EXTRA_CORRECT_ANSWERS,0)
        totalQuestions = intent.getIntExtra(EXTRA_TOTAL_QUESTIONS,0)
        hintsUsed = intent.getIntExtra(EXTRA_HINTS_USED,0)

        showScore()

        binding.resetButton.setOnClickListener {
            resetAllEvent()
        }
    }

    private fun resetAllEvent(){
        binding.resetButton.isEnabled = false
        setResetAllResult(true)
    }

    private fun setResetAllResult(resetALlClicked: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_RESET_ALL,resetALlClicked)
        }
        setResult(Activity.RESULT_OK,data)
    }


    private fun showScore(){
        binding.correctAnswersValue.text = correctAnswers.toString()
        binding.totalQuestionsValue.text = totalQuestions.toString()
        binding.hintsUsedValue.text = hintsUsed.toString()
    }

    //access functions without having an instance of a class
    companion object{
        fun newIntent(packageContext: Context,
                      correctAnswers: Int,
                      totalQuestions: Int,
                      hintsUsed: Int): Intent{
            return Intent(packageContext, ResultActivity::class.java).apply {
                //key, value
                putExtra(EXTRA_CORRECT_ANSWERS,correctAnswers)
                putExtra(EXTRA_TOTAL_QUESTIONS,totalQuestions)
                putExtra(EXTRA_HINTS_USED,hintsUsed)
            }
        }
    }
}