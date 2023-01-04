package edu.vt.cs5254.multiquiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val questionResId: Int,
    val answerList: List<Answer>
)