package com.example.appcore2

object PriceCalculator {
    fun calculateTotalPrice(price: Int, days: Int): Int {
        return price * days
    }
}