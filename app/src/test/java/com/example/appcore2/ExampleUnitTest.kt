package com.example.appcore2

import org.junit.Test

import org.junit.Assert.*

class SelectUnitTest {
    @Test
    fun testCalculateTotalPrice() {
        val price = 50 // Example price
        val days = 3 // Example number of days
        val expectedResult = 150 // The expected result of calculateTotalPrice

        // Call the function and check if it returns the expected result
        val result = PriceCalculator.calculateTotalPrice(price, days)
        assertEquals(expectedResult, result)
    }
}






