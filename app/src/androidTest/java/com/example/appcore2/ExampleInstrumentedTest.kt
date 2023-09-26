package com.example.appcore2

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun pickcar() {
        onView(withId(R.id.next)).perform(click())
        onView(withId(R.id.borrow)).perform(click())
        onView(withId(R.id.selector)).perform(swipeLeft()) // Assuming swipe left corresponds to decreasing the value
        onView(withId(R.id.save)).perform(click())
        onView(withId(R.id.selector)).perform(swipeRight()) // Assuming swipe left corresponds to decreasing the value
        onView(withId(R.id.save)).perform(click())
        onView(withId(R.id.next)).perform(click())
        onView(withId(R.id.borrow)).perform(click())
        Espresso.pressBack()

    }
}
