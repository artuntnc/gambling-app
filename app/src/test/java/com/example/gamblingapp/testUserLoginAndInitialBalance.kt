package com.example.gamblingapp

import com.example.gamblingapp.data.LoginState
import com.example.gamblingapp.data.User
import com.example.gamblingapp.data.UserService

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class testUserLoginAndInitialBalance {

    private lateinit var loginState: LoginState
    private lateinit var userService: UserService

    @Before
    fun setUp() {
        // Mock dependencies
        loginState = mock()
        userService = mock()
    }

    @Test
    fun testUserLoginAndInitialBalance() {
        // Arrange
        val testUser = User(
            fullName = "testUser",
            balance = 1000F,
            email = "test1@example.com",
            password = "testUser123",
            pesel = "12312312312",
            birthDate = "10/04/2000"
        )

        // Mock login behavior
        whenever(loginState.login(testUser.email, testUser.password))
            .thenReturn(Result.success(true))

        // Mock user balance retrieval
        whenever(userService.getUserBalance(testUser.email))
            .thenReturn(testUser.balance)

        // Act
        val loginResult = loginState.login(testUser.email, testUser.password)

        // Assert
        assertTrue(loginResult.isSuccess)

        val userBalance = userService.getUserBalance(testUser.email)
        assertEquals(1000F, userBalance)
    }
}
