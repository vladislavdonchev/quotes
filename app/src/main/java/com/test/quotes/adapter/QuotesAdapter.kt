package com.test.quotes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.quotes.R
import com.test.quotes.activities.AlarmActivity
import com.test.quotes.model.QuoteRowViewModel

class QuotesAdapter(
    context: Context, private val quoteRows: ArrayList<QuoteRowViewModel>
) : RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {

    private val context: Context = context
    private val itemClickListener = ItemClickListener()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): QuotesAdapter.ViewHolder {
        val quoteRow = LayoutInflater.from(context).inflate(R.layout.item_quote, parent, false)
        quoteRow.setOnClickListener(itemClickListener)
        return ViewHolder(quoteRow)
    }

    override fun getItemCount(): Int {
        return quoteRows.size
    }

    override fun onBindViewHolder(holder: QuotesAdapter.ViewHolder, position: Int) {
        // Dynamic data stuff goes here.
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // Map this to the ViewModel.
    }

    inner class ItemClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            // This should use view tags in order to know the item position.
            context.startActivity(Intent(context, AlarmActivity::class.java))
        }
    }
}