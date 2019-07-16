package com.test.quotes.data.listeners

import com.test.quotes.data.model.Quote

interface QuoteListener {

    fun onQuoteReceived(quote: Quote)
}