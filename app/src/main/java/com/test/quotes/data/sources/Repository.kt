package com.test.quotes.data.sources

import android.os.Handler
import com.test.quotes.data.listeners.QuoteListener
import com.test.quotes.data.model.Quote
import kotlin.random.Random

object Repository {

    private val randomQuoteHandler = Handler()
    private val random = Random.Default

    // TODO It would be better to use a proper observer pattern with LiveData or some type of EventBus instead of listeners.
    // This however is good enough for the UI mock testing.
    private val quoteListeners = ArrayList<QuoteListener>()

    private val mockSymbols = arrayOf(
        "EUR/USD",
        "USD/JPY",
        "GBP/USD",
        "AUD/USD",
        "USD/CAD",
        "USD/CNY",
        "USD/CHF",
        "EUR/GBP",
        "USD/MXN",
        "USD/SGD"
    )

    // Needed to be able to reference to a runnable instance from a lambda.
    private fun runnable(body: Runnable.(Runnable)->Unit) = object: Runnable {
        override fun run() {
            this.body(this)
        }
    }

    private val randomQuoteRunnable = runnable {
        generateQuote()
        randomQuoteHandler.postDelayed(this, random.nextLong(100, 200))
    }

    private fun generateQuote(symbol: String = "") {
        val newQuote = Quote(
            if (symbol.isBlank()) mockSymbols[random.nextInt(mockSymbols.size)] else symbol,
            random.nextDouble(1.12340, 1.12345),
            random.nextDouble(1.12340, 1.12345))

        for (quoteListener: QuoteListener in quoteListeners) {
            quoteListener.onQuoteReceived(newQuote)
        }
    }

    fun startGeneratingQuotes() {
        for (symbol: String in mockSymbols) {
            generateQuote(symbol)
        }

        randomQuoteHandler.postDelayed(randomQuoteRunnable, random.nextLong(100, 200))
    }

    fun stopGeneratingQuotes() {
        randomQuoteHandler.removeCallbacksAndMessages(randomQuoteRunnable)
    }

    fun registerListener(quoteListener: QuoteListener) {
        quoteListeners.add(quoteListener)
    }

    fun unregisterListener(quoteListener: QuoteListener) {
        quoteListeners.remove(quoteListener)
    }
}