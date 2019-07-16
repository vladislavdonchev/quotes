package com.test.quotes.model

import android.os.Parcel
import android.os.Parcelable
import com.test.quotes.data.model.Quote
import com.test.quotes.util.Constants.Companion.COLOR_GREEN
import com.test.quotes.util.Constants.Companion.COLOR_RED

data class QuoteRowViewModel(
    var lastQuote: Quote?,

    var sellColor: Int = COLOR_RED,
    var buyColor: Int = COLOR_RED,
    var spread: Double = 0.0,
    var spreadColor: Int = COLOR_RED
) : Parcelable {

    fun update(newQuote: Quote) {
        // TODO Do any presentation-related price formatting here.
        // Just convert it to string as it is for now.
        val sellDiff = newQuote.sellPrice - lastQuote!!.sellPrice
        val buyDiff = newQuote.buyPrice - lastQuote!!.buyPrice
        sellColor = if (sellDiff < 0) COLOR_RED else COLOR_GREEN
        buyColor = if (buyDiff < 0) COLOR_RED else COLOR_GREEN

        spread = ((newQuote.sellPrice - newQuote.buyPrice) / newQuote.sellPrice) * 100
        spreadColor = if (spread < 0) COLOR_RED else COLOR_GREEN

        lastQuote = newQuote
    }

    constructor(source: Parcel) : this(
        source.readParcelable<Quote>(Quote::class.java.classLoader),
        source.readInt(),
        source.readInt(),
        source.readDouble(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(lastQuote, 0)
        writeInt(sellColor)
        writeInt(buyColor)
        writeDouble(spread)
        writeInt(spreadColor)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<QuoteRowViewModel> = object : Parcelable.Creator<QuoteRowViewModel> {
            override fun createFromParcel(source: Parcel): QuoteRowViewModel = QuoteRowViewModel(source)
            override fun newArray(size: Int): Array<QuoteRowViewModel?> = arrayOfNulls(size)
        }
    }
}