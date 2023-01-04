package edu.vt.cs5254.multiquiz

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseMultiQuizTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun appContextGivesCorrectPackageName() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("edu.vt.cs5254.multiquiz", appContext.packageName)
    }

    @Test
    fun clickingWrongAnswerEnableSubmitButton() {
        onView(withId(R.id.answer_1_button))
            .perform(click())
        onView(withId(R.id.submit_button))
            .check(matches(isEnabled()))
    }

    @Test
    fun clickingHint3TimesDisablesAllButCorrect() {
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.answer_0_button))
            .check(matches(isEnabled()))
        onView(withId(R.id.answer_1_button))
            .check(matches(not(isEnabled())))
        onView(withId(R.id.answer_2_button))
            .check(matches(not(isEnabled())))
        onView(withId(R.id.answer_3_button))
            .check(matches(not(isEnabled())))
    }


    @Test
    fun clickingHint3TimesDisablesHint() {
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun clickingAnswer0ThenAnswer1DeselectsAnswer0AndSelectsAnswer1() {
        onView(withId(R.id.answer_0_button))
            .perform(click())
        onView(withId(R.id.answer_1_button))
            .perform(click())
        onView(withId(R.id.answer_0_button))
            .check(matches(not(isSelected())))
        onView(withId(R.id.answer_1_button))
            .check(matches(isSelected()))
    }

    @Test
    fun clickingAnswer1TwiceDeselectsAnswer1() {
        onView(withId(R.id.answer_1_button))
            .perform(click())
        onView(withId(R.id.answer_1_button))
            .perform(click())
        onView(withId(R.id.answer_1_button))
            .check(matches(not(isSelected())))
    }

    @Test
    fun clickingAnswer1ThenRotatingSelectsAnswer1() {
        onView(withId(R.id.answer_1_button))
            .perform(click())
        scenario.recreate()
        onView(withId(R.id.answer_1_button))
            .check(matches(isSelected()))
    }

    @Test
    fun clickingHint3TimesThenRotatingDisablesHint() {
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        scenario.recreate()
        onView(withId(R.id.hint_button))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun clickingHint3TimesThenRotatingTwiceDisablesAllButCorrect() {
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        scenario.recreate()
        scenario.recreate()
        onView(withId(R.id.answer_0_button))
            .check(matches(isEnabled()))
        onView(withId(R.id.answer_1_button))
            .check(matches(not(isEnabled())))
        onView(withId(R.id.answer_2_button))
            .check(matches(not(isEnabled())))
        onView(withId(R.id.answer_3_button))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun all4QuestionsCorrectOneHint() {
        onView(withId(R.id.answer_0_button))
            .perform(click())
        onView(withId(R.id.hint_button))
            .perform(click())
        onView(withId(R.id.submit_button))
            .perform(click())
        onView(withId(R.id.answer_1_button))
            .perform(click())
        onView(withId(R.id.submit_button))
            .perform(click())
        onView(withId(R.id.answer_2_button))
            .perform(click())
        onView(withId(R.id.submit_button))
            .perform(click())
        onView(withId(R.id.answer_3_button))
            .perform(click())
        onView(withId(R.id.submit_button))
            .perform(click())
        // you should now be in the result activity
        onView(withId(R.id.total_questions_value))
            .check(matches(withText("4")))
        onView(withId(R.id.correct_answers_value))
            .check(matches(withText("4")))
        onView(withId(R.id.hints_used_value))
            .check(matches(withText("1")))
    }

}