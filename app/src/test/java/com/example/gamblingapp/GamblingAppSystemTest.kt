package com.example.gamblingapp

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.ui.GamblingAppViewModel
import com.example.gamblingapp.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GamblingAppSystemTest {

    @get:Rule
    val activityRule = GamblingAppViewModel(LoginState::class.java)

    @Test
    fun testCompleteRouletteGameFlow() {
        // Step 1: Enter email and password, then login
        onView(withId(R.id.emailInput))
            .perform(typeText("test1@example.com"), closeSoftKeyboard())
        onView(withId(R.id.passwordInput))
            .perform(typeText("testUser123"), closeSoftKeyboard())
        onView(withId(R.id.loginButton))
            .perform(click())

        // Step 2: Verify initial balance is displayed
        onView(withId(R.id.balanceTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("Balance: $1000")))

        // Step 3: Navigate to Roulette game screen
        onView(withId(R.id.Roulettebutton))
            .perform(click())

        // Step 4: Place a bet
        onView(withId(R.id.betInput))
            .perform(typeText("100"), closeSoftKeyboard())
        onView(withId(R.id.placeBetButton))
            .perform(click())

        // Step 5: Verify balance update
        onView(withId(R.id.balanceTextView))
            .check(matches(isDisplayed()))
            .check(matches(not(withText("Balance: $1000")))) // Ensure balance changed
    }
}
