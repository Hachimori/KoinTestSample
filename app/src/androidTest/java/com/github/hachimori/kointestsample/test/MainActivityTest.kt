package com.github.hachimori.kointestsample.test


import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.hachimori.kointestsample.R
import com.github.hachimori.kointestsample.ui.MainActivity
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var testRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        onView(withId(R.id.input_form_github_name))
            .perform(replaceText("Hachimori"), closeSoftKeyboard())

        onView(withId(R.id.input_form_get_user_info))
            .perform(click())

        SystemClock.sleep(3000)

        onView(withId(R.id.input_form_user_info))
            .check(matches(withText(containsString("Ben Hachimori"))))

        onView(withId(R.id.input_form_get_repository))
            .perform(click())

        SystemClock.sleep(3000)

        onView(withId(R.id.input_form_repository_list))
            .perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(withId(R.id.repository_detail_repository))
            .check(matches(withText("AndroidSamples")))

        SystemClock.sleep(3000)
    }
}
