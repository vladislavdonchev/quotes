package com.test.quotes.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.quotes.R
import com.test.quotes.adapter.QuotesAdapter
import com.test.quotes.model.QuoteRowViewModel

class QuotesActivity : AppCompatActivity() {

    companion object {
        lateinit var quotesListView: RecyclerView
        var quoteList = ArrayList<QuoteRowViewModel>()
        private var adapter: QuotesAdapter? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        initViews()
        mockQuotes()
    }

    private fun initViews() {
        quotesListView = findViewById<View>(R.id.activity_main_quotes) as RecyclerView
        quotesListView.layoutManager = LinearLayoutManager(this)

        adapter = QuotesAdapter(this@QuotesActivity, quoteList)
        quotesListView.adapter = adapter

        quotesListView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun mockQuotes() {
        repeat(72) {
            quoteList.add(QuoteRowViewModel())
        }

        adapter!!.notifyDataSetChanged()
    }
}