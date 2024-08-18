package edu.vt.cs5254.multiquiz

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java);
    }

    @After
    fun tearDown() {
        scenario.close();
    }

    @Test
    fun showsFirstQuestionOnLaunch() {
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.capitalQuestion)))
    }

    @Test
    fun showsSecondQuestionAfterNextPress() {
        onView(withId(R.id.answer_0_button)).perform(click())
        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.oceanQuestion)))
    }
    @Test
    fun handlesActivityRecreation() {
        onView(withId(R.id.answer_0_button)).perform(click())
        onView(withId(R.id.submit_button)).perform(click())
        scenario.recreate()
        onView(withId(R.id.question_text_view))
            .check(matches(withText(R.string.oceanQuestion)))
    }
}