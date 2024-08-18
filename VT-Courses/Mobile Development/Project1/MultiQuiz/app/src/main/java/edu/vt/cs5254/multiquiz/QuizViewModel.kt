package edu.vt.cs5254.multiquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val IS_NEW_PROBLEM_REMAINING = "is_new_problem_remaning"

class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.capitalQuestion,
            listOf(
                Answer(R.string.capital_0_answer,true),
                Answer(R.string.capital_1_answer,false),
                Answer(R.string.capital_2_answer,false),
                Answer(R.string.capital_3_answer,false)
            ),
        ),
        Question(R.string.oceanQuestion,
            listOf(
                Answer(R.string.ocean_0_answer,false),
                Answer(R.string.ocean_1_answer,true),
                Answer(R.string.ocean_2_answer,false),
                Answer(R.string.ocean_3_answer,false)
            )),
        Question(R.string.landSizeQuestion,
            listOf(
                Answer(R.string.landSize_0_answer,false),
                Answer(R.string.landSize_1_answer,false),
                Answer(R.string.landSize_2_answer,true),
                Answer(R.string.landSize_3_answer,false)
            )),
        Question(R.string.richestQuestion,
            listOf(
                Answer(R.string.richest_0_answer,false),
                Answer(R.string.richest_1_answer,false),
                Answer(R.string.richest_2_answer,false),
                Answer(R.string.richest_3_answer,true)
            )
        )
    )
    private var questionIndex: Int = 0

    var correctAnswers = 0
    val totalQuestions = questionBank.size
    var hintsUsed =0

    var newProblemRemaining :Boolean
        get() = savedStateHandle.get(IS_NEW_PROBLEM_REMAINING) ?: true
        set(value) = savedStateHandle.set(IS_NEW_PROBLEM_REMAINING, value)

    val question
        get() = questionBank[questionIndex].questionResId

    val answerList
        get() = questionBank[questionIndex].answerList

    fun nextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank.size
    }

    fun countCorrect(){
        var count = 0
        questionBank.forEach {
            it.answerList.forEach { answer->
                if (answer.isSelected) count++
            }
        }
        correctAnswers = count
    }

    fun  giveHint(){
        val hideAnswer = answerList
            .filter { !it.isCorrect }
            .filter { it.isEnabled }
            .random()
        hideAnswer.isEnabled = false
        hideAnswer.isSelected = false
        hintsUsed ++
    }

    fun lastQuestion(): Boolean{
        val result = questionIndex == questionBank.size-1
        if (result) newProblemRemaining = false
        return result
    }

     fun resetAll(){
        if(!newProblemRemaining) return
         correctAnswers = 0
         hintsUsed = 0
        questionBank.forEach {
            it.answerList.forEach { answer ->
                answer.isEnabled = true
                answer.isSelected = false
            }
        }

    }


}