package com.example.myapplicationsixth

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UITest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() {
        onView(withId(R.id.view_pager2)).perform(swipeLeft())
        onView(withId(R.id.view_pager2)).perform(swipeRight())
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.btnSave)).perform(click())
    }

}
