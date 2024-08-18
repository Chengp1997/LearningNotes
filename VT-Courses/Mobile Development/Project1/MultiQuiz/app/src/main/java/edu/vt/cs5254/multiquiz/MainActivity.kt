package edu.vt.cs5254.multiquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import edu.vt.cs5254.multiquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Name: Geping Chen
    // PID: gepingc

    private lateinit var binding: ActivityMainBinding

    private lateinit var buttonList: List<Button>

    private val quizViewModel: QuizViewModel by viewModels()

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result->
        quizViewModel.nextQuestion()
        if (result.resultCode == Activity.RESULT_OK){
            quizViewModel.newProblemRemaining = result.data?.getBooleanExtra(EXTRA_RESET_ALL,false)?:false
        }
        quizViewModel.resetAll()
        updateProblem()
        updateButtons()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initialize view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonList = listOf(
            binding.answer0Button,
            binding.answer1Button,
            binding.answer2Button,
            binding.answer3Button
        )

        updateButtons()
        updateProblem()
    }

    private fun updateProblem(){
        binding.questionTextView.setText(quizViewModel.question);
        buttonList.zip(quizViewModel.answerList).forEach {
                (button,answer) -> button.setText(answer.textResId)
        }
        buttonList.zip(quizViewModel.answerList).forEach{
                (button,answer)->
            button.setOnClickListener {
                answerClickedEvent(answer)
            }
        }

        binding.hintButton.setOnClickListener {
            hintClickedEvent()
        }
        binding.submitButton.setOnClickListener {
            submitClickedEvent()
        }
    }

    private fun answerClickedEvent(answer: Answer){
        answer.isSelected = !answer.isSelected
        quizViewModel.answerList.filter { it!=answer}.forEach{it.isSelected=false}
        updateButtons()
    }

    private fun hintClickedEvent(){
        quizViewModel.giveHint()
        updateButtons()
    }

    private fun submitClickedEvent(){
        quizViewModel.countCorrect()
        if(quizViewModel.lastQuestion()){
            jumpToResultActivity()
        }else{
            quizViewModel.nextQuestion()
            updateProblem()
            updateButtons()
        }
    }

    private fun jumpToResultActivity(){
        val intent = ResultActivity.newIntent(
            this@MainActivity,
            quizViewModel.correctAnswers,
            quizViewModel.totalQuestions,
            quizViewModel.hintsUsed
        )
        resultLauncher.launch(intent)
    }

    private fun updateButtons(){
        updateAnswerButtons()
        updateHintButton()
        updateSubmitButton()
    }

    //update the button status,based on the answer states
    private fun updateAnswerButtons(){
        buttonList.zip(quizViewModel.answerList).forEach{
                (button,answer) ->
            button.isSelected = answer.isSelected
            button.isEnabled = answer.isEnabled
            button.updateColor()
        }

    }

    private fun updateSubmitButton(){
        binding.submitButton.isEnabled = quizViewModel.answerList.any { it.isSelected }
    }

    private fun updateHintButton(){
        binding.hintButton.isEnabled =
            quizViewModel.answerList.filterNot { it.isCorrect }.any { it.isEnabled }
    }



}