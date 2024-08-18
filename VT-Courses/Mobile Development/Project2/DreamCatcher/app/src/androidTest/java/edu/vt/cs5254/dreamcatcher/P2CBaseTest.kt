package edu.vt.cs5254.dreamcatcher

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.UriMatchers.*
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.intent.IntentCallback
import androidx.test.runner.intent.IntentMonitorRegistry
import com.google.android.material.R.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class P2CBaseTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun addNewDreamAppBar() {
        onView(withId(R.id.new_dream))
            .check(matches(instanceOf(ActionMenuItemView::class.java)))
            .perform(click())
        onView(withId(R.id.title_text))
            .check(matches(withText("")))
        onView(withId(R.id.fulfilled_checkbox))
            .check(matches(not(isChecked())))
        onView(withId(R.id.deferred_checkbox))
            .check(matches(not(isChecked())))
    }

    @Test
    fun addNewDreamAddReflectionSetDeferred() {
        onView(withId(R.id.new_dream))
            .perform(click())
        onView(withId(R.id.title_text))
            .perform(replaceText("New Dream"))
            .perform(closeSoftKeyboard())
        addReflection("Just Starting")
        onView(withId(R.id.deferred_checkbox))
            .perform(click())
        addReflection("Needed to defer")
        pressBack()
        onView(withId(R.id.dream_recycler_view))
            .check(
                matches(
                    atPosition(
                        0,
                        allOf(
                            hasDescendant(withText("New Dream")),
                            hasDescendant(withText("Reflections: 2")),
                            hasDescendant(
                                allOf(
                                    withId(R.id.list_item_image),
                                    matchesDrawable(R.drawable.dream_deferred_icon),
                                    withEffectiveVisibility(Visibility.VISIBLE)
                                )
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun swipeLeftDeleteDreamTwo() {
        onView(withText("Ride in a hot air balloon"))
            .perform(swipeLeft())
        onView(withId(R.id.dream_recycler_view))
            .check(matches(atPosition(2, hasDescendant(withText("Foster or adopt a pet")))))
    }

    @Test
    fun swipeRightNotDeleteDreamFour() {
        onView(withText("Earn a graduate degree"))
            .perform(swipeRight())
        onView(withId(R.id.title_text))
            .check(matches(withText("Earn a graduate degree")))
        pressBack()
        onView(withId(R.id.dream_recycler_view))
            .check(matches(atPosition(4, hasDescendant(withText("Earn a graduate degree")))))
    }

    @Test
    fun swipeDeleteAllCheckNotEmpty() {
        onView(withId(R.id.no_dream_text))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.no_dream_add_button))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.dream_recycler_view)).perform(
            repeatedlyUntil(
                actionOnItemAtPosition<DreamHolder>(0, swipeLeft()),
                hasChildCount(0),
                22
            )
        )
        onView(withId(R.id.no_dream_text))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(instanceOf(TextView::class.java)))
        onView(withId(R.id.no_dream_add_button))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(instanceOf(Button::class.java)))
    }

    @Test
    fun swipeDeleteAllAddDream() {
        onView(withId(R.id.dream_recycler_view)).perform(
            repeatedlyUntil(
                actionOnItemAtPosition<DreamHolder>(0, swipeLeft()),
                hasChildCount(0),
                22
            )
        )
        onView(withId(R.id.no_dream_add_button))
            .perform(click())
        onView(withId(R.id.title_text))
            .perform(replaceText("First Dream"))
            .perform(closeSoftKeyboard())
        pressBack()
        onView(withId(R.id.dream_recycler_view))
            .check(
                matches(
                    atPosition(
                        0,
                        allOf(
                            hasDescendant(withText("First Dream")),
                            hasDescendant(withText("Reflections: 0")),
                            hasDescendant(
                                allOf(
                                    withId(R.id.list_item_image),
                                    withEffectiveVisibility(Visibility.GONE)
                                )
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun shareDreamSix() {
        Intents.init()
        onView(withText("Swim with dolphins"))
            .perform(click())
        onView(withId(R.id.share_dream_menu))
            .check(matches(instanceOf(ActionMenuItemView::class.java)))
            .perform(click())
        intended(chooser(hasAction(Intent.ACTION_SEND)))
        intended(
            chooser(
                hasExtra(
                    containsString(Intent.EXTRA_TEXT),
                    allOf(
                        startsWith("Swim with dolphins"),
                        containsString("Last updated 2022-09-10"),
                        containsString("Reflections:"),
                        containsString(" * One"),
                        containsString(" * Two"),
                        endsWith("This dream has been Fulfilled.")
                    )
                )
            )
        )
        Intents.release()
    }

    @Test
    fun shareDreamEight() {
        Intents.init()
        onView(withText("Travel to every continent"))
            .perform(click())
        onView(withId(R.id.share_dream_menu))
            .check(matches(instanceOf(ActionMenuItemView::class.java)))
            .perform(click())
        intended(chooser(hasAction(Intent.ACTION_SEND)))
        intended(
            chooser(
                hasExtra(
                    containsString(Intent.EXTRA_TEXT),
                    allOf(
                        startsWith("Travel to every continent"),
                        containsString("Last updated 2022-09-10"),
                        not(containsString("Reflections:")),
                        not(containsString(" * ")),
                        endsWith("This dream has been Deferred.")
                    )
                )
            )
        )
        Intents.release()
    }

    @Test
    fun takePhotoCheckIntent() {
        Intents.init()
        onView(withText("Earn a graduate degree"))
            .perform(click())
        onView(withId(R.id.take_photo_menu))
            .check(matches(instanceOf(ActionMenuItemView::class.java)))
            .perform(click())
        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        intended(
            hasExtra(
                containsString("output"),
                allOf(
                    hasScheme("content"),
                    hasHost("edu.vt.cs5254.dreamcatcher.fileprovider"),
                    hasPath(startsWith("/dream_photos/")),
                    hasPath(endsWith("IMG_01234567-89ab-cdef-fedc-ba9876543215.JPG"))
                )
            )
        )
        Intents.release()
    }

    @Test
    fun takePhotoCheckResult() {
        Intents.init()

        val tgtContext = InstrumentationRegistry.getInstrumentation().targetContext

        IntentMonitorRegistry.getInstance().addIntentCallback(object : IntentCallback {
            override fun onIntentSent(intent: Intent?) {
                val uri: Uri = intent?.getParcelableExtra(MediaStore.EXTRA_OUTPUT) ?: return
                val icon = BitmapFactory.decodeResource(
                    tgtContext.resources,
                    R.drawable.dream_fulfilled_icon
                )
                val os = tgtContext.contentResolver.openOutputStream(uri)
                os?.run {
                    icon.compress(Bitmap.CompressFormat.JPEG, 100, this)
                    flush()
                    close()
                }
            }
        })

        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(
            ActivityResult(Activity.RESULT_OK, Intent())
        )

        onView(withText("Earn a graduate degree"))
            .perform(click())
        onView(withId(R.id.dream_photo))
            .check(matches(withTagValue(nullValue())))

        onView(withId(R.id.take_photo_menu))
            .perform(click())

        onView(withId(R.id.dream_photo))
            .check(matches(withTagValue(`is`("IMG_01234567-89ab-cdef-fedc-ba9876543215.JPG"))))

        Intents.release()
    }

    @Test
    fun checkZoomDisabledWithoutPhoto() {

        val neutral30color = ContextCompat.getColor(
            InstrumentationRegistry.getInstrumentation().targetContext,
            color.material_dynamic_neutral30
        )

        onView(withText("Earn a graduate degree"))
            .perform(click())
        onView(withId(R.id.dream_photo))
            .check(matches(withTagValue(nullValue())))
            .check(matches(not(isEnabled())))
            .check(matches(withBackgroundColor(neutral30color)))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun checkZoomPhoto() {
        takePhotoCheckResult()
        onView(withId(R.id.dream_photo))
            .perform(click())
        onView(withId(R.id.photo_detail))
            .inRoot(isDialog())
            .check(matches(instanceOf(ImageView::class.java)))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun checkDreamEntryRecycler() {
        onView(withText("Foster or adopt a pet"))
            .perform(click())
        onView(withId(R.id.dream_entry_recycler))
            .check(
                matches(
                    atPosition(
                        0,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("CONCEIVED")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        1,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("One")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        2,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Two")
                            )
                        )
                    )
                )
            )

            .check(
                matches(
                    atPosition(
                        3,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Three")
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun scrollDreamEntryRecycler() {
        onView(withText("Foster or adopt a pet"))
            .perform(click())
        addReflection("Four")
        addReflection("Five")
        addReflection("Six")
        addReflection("Seven")
        addReflection("Eight")
        addReflection("Nine")
        addReflection("Ten")
        onView(withId(R.id.deferred_checkbox)).perform(click())

        onView(withId(R.id.dream_entry_recycler))
            .perform(scrollToPosition<DreamEntryHolder>(0))
            .check(
                matches(
                    atPosition(
                        4,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Four")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        5,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Five")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        6,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Six")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        7,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Seven")
                            )
                        )
                    )
                )
            )
            .perform(scrollToPosition<DreamEntryHolder>(11))
            .check(
                matches(
                    atPosition(
                        10,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("Ten")
                            )
                        )
                    )
                )
            )
            .check(
                matches(
                    atPosition(
                        11,
                        hasDescendant(
                            allOf(
                                instanceOf(Button::class.java),
                                not(isEnabled()),
                                withText("DEFERRED")
                            )
                        )
                    )
                )
            )

    }

    @Test
    fun swipeDeleteReflections() {
        onView(withText("Foster or adopt a pet"))
            .perform(click())
        onView(withId(R.id.dream_entry_recycler))
            .check(matches(hasChildCount(4)))
        onView(withText("One"))
            .perform(swipeLeft())
        onView(withText("Two"))
            .perform(swipeLeft())
        onView(withText("Three"))
            .perform(swipeLeft())
        onView(withId(R.id.dream_entry_recycler))
            .check(matches(hasChildCount(1)))
        pressBack()
        onView(withId(R.id.dream_recycler_view))
            .check(matches(atPosition(0, hasDescendant(withText("Reflections: 0")))))
    }

    @Test
    fun swipeNonReflectionsAndSwipeRightReflections() {
        onView(withText("Ride in a hot air balloon"))
            .perform(click())
        onView(withId(R.id.dream_entry_recycler))
            .check(matches(hasChildCount(4)))
        onView(withText("CONCEIVED"))
            .perform(swipeLeft())
        onView(withText("DEFERRED"))
            .perform(swipeLeft())
        onView(withText("One"))
            .perform(swipeRight())
        onView(withText("Two"))
            .perform(swipeRight())
        onView(withId(R.id.dream_entry_recycler))
            .check(matches(hasChildCount(4)))
    }


    //  ------ PRIVATE HELPER METHODS BELOW HERE ------

    private fun withBackgroundColor(@ColorRes color: Int): BoundedMatcher<View, ImageView> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("image view with background color")
            }

            override fun matchesSafely(item: ImageView): Boolean {
                val colorDrawable = item.background as ColorDrawable
                return colorDrawable.color == color
            }
        }
    }

    private fun matchesDrawable(resourceID: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, ImageView>(ImageView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("an ImageView with resourceID: ")
                description.appendValue(resourceID)
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                val expBM = imageView.context.resources
                    .getDrawable(resourceID, null).toBitmap()
                return imageView.drawable?.toBitmap()?.sameAs(expBM) ?: false
            }
        }
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }

    private fun addReflection(reflectionText: String) {
        onView(withId(R.id.add_reflection_button))
            .perform(click())
        onView(withId(R.id.reflection_text))
            .perform(replaceText(reflectionText))
        onView(withText("Add"))
            .inRoot(isDialog())
            .perform(click())
    }

    private fun chooser(matcher: Matcher<Intent>): Matcher<Intent> {
        return allOf(
            hasAction(Intent.ACTION_CHOOSER),
            hasExtra(`is`(Intent.EXTRA_INTENT), matcher)
        )
    }

}