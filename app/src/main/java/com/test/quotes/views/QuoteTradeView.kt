package com.test.quotes.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.test.quotes.R
import kotlinx.android.synthetic.main.view_quote_trade.view.*

class QuoteTradeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_quote_trade, this, true)
    }

    fun setup(actionText: String, symbolText: String? = "") {
        quote_trade_header.text = symbolText
        quote_trade_button.text = actionText

        quote_trade_header.setTypeface(null, if (symbolText!!.isBlank()) Typeface.NORMAL else Typeface.BOLD)
    }

    fun update(priceText: String, priceColor: Int, spreadText: String = "", spreadColor: Int = Color.BLACK) {
        //TODO These views can be made into class members instead of calling findViewById() on each update.
        quote_trade_price.text = priceText
        quote_trade_price.setTextColor(priceColor)

        if (spreadText.isNotBlank()) {
            quote_trade_header.text = spreadText
            quote_trade_header.setTextColor(spreadColor)
        }
    }
}