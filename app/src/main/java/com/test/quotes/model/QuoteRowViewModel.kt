package com.test.quotes.model

data class QuoteRowViewModel(
    var symbol: String = "EUR/USD",
    var sellPrice: Double = 0.12345,
    var buyPrice: Double = 0.12345
)