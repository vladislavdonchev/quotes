package com.test.quotes.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.test.quotes.R
import com.test.quotes.activities.AlarmActivity
import com.test.quotes.model.QuoteRowViewModel
import com.test.quotes.util.Constants.Companion.COLOR_RED
import com.test.quotes.views.QuoteRowView
import com.test.quotes.views.QuoteTradeView
import java.text.DecimalFormat

class QuotesAdapter(
    context: Context, private val quoteList: List<QuoteRowViewModel>
) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    private val context: Context = context
    private val itemClickListener = ItemClickListener()

    private val priceFormat: DecimalFormat = DecimalFormat("0.00000")
    private val spreadFormat: DecimalFormat = DecimalFormat("0.00%")

    private val redTextColor: Int = ContextCompat.getColor(context, R.color.redText)
    private val greenTextColor: Int = ContextCompat.getColor(context, R.color.greenText)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val quoteRow = QuoteRowView(context)
        quoteRow.setOnClickListener(itemClickListener)
        return ViewHolder(quoteRow)
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quoteRowViewModel = quoteList[position]

        val sellColor = if (quoteRowViewModel.sellColor == COLOR_RED) redTextColor else greenTextColor
        val buyColor = if (quoteRowViewModel.buyColor == COLOR_RED) redTextColor else greenTextColor
        val spreadColor = if (quoteRowViewModel.spreadColor == COLOR_RED) redTextColor else greenTextColor

        holder.sellQuoteView?.setup("SELL", quoteRowViewModel.lastQuote?.symbol)
        holder.buyQuoteView?.setup("BUY")

        holder.sellQuoteView?.update(
            priceFormat.format(quoteRowViewModel.lastQuote?.sellPrice),
            sellColor
        )
        holder.buyQuoteView?.update(
            priceFormat.format(quoteRowViewModel.lastQuote?.buyPrice),
            buyColor,
            spreadFormat.format(quoteRowViewModel.spread),
            spreadColor
        )
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var sellQuoteView: QuoteTradeView? = null
        var buyQuoteView: QuoteTradeView? = null

        init {
            sellQuoteView = itemView.findViewById(R.id.item_quote_sell)
            buyQuoteView = itemView.findViewById(R.id.item_quote_buy)
        }
    }

    inner class ItemClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            // This should use view tags in order to know the item position.
            context.startActivity(Intent(context, AlarmActivity::class.java))
        }
    }
}