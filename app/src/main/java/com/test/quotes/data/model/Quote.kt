package com.test.quotes.data.model

import android.os.Parcel
import android.os.Parcelable

data class Quote(
    val symbol: String?,
    val sellPrice: Double,
    val buyPrice: Double
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readDouble(),
        source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(symbol)
        writeDouble(sellPrice)
        writeDouble(buyPrice)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Quote> = object : Parcelable.Creator<Quote> {
            override fun createFromParcel(source: Parcel): Quote = Quote(source)
            override fun newArray(size: Int): Array<Quote?> = arrayOfNulls(size)
        }
    }
}