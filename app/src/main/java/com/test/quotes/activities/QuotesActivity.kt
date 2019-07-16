package com.test.quotes.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.test.quotes.R
import com.test.quotes.adapter.QuotesAdapter
import com.test.quotes.data.listeners.QuoteListener
import com.test.quotes.data.model.Quote
import com.test.quotes.data.sources.Repository
import com.test.quotes.model.QuoteRowViewModel

class QuotesActivity : AppCompatActivity(), QuoteListener {

    private lateinit var quotesListView: RecyclerView
    private lateinit var adapter: QuotesAdapter

    // TODO These should be persisted by the Repository instead.
    // For the sake of the demo, we will use Serializable/Parcelable
    // interfaces and the Activity Instance State.
    private val QUOTE_POSITION_MAP_KEY = "QuotePositionMapKey"
    private val QUOTE_LIST_KEY = "QuotePositionList"

    private var quotePositionMap = HashMap<String, Int>()
    private var quoteList = ArrayList<QuoteRowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
    }

    override fun onResume() {
        super.onResume()

        initViews()
        Repository.registerListener(this)
        Repository.startGeneratingQuotes()
    }

    override fun onPause() {
        super.onPause()

        Repository.stopGeneratingQuotes()
        Repository.unregisterListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(QUOTE_POSITION_MAP_KEY, quotePositionMap)
        outState.putParcelableArrayList(QUOTE_LIST_KEY, quoteList)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        quotePositionMap = savedInstanceState.getSerializable(QUOTE_POSITION_MAP_KEY) as HashMap<String, Int>
        quoteList = savedInstanceState.getParcelableArrayList<QuoteRowViewModel>(QUOTE_LIST_KEY) as ArrayList<QuoteRowViewModel>
        super.onRestoreInstanceState(savedInstanceState)
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
        (quotesListView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun onQuoteReceived(quote: Quote) {
        handleQuote(quote)
    }

    private fun handleQuote(quote: Quote) {
        if (quotePositionMap[quote.symbol] == null) {
            quoteList.add(QuoteRowViewModel(quote))
            quotePositionMap[quote.symbol!!] = quoteList.size - 1
        }

        quoteList[quotePositionMap[quote.symbol]!!].update(quote)
        adapter.notifyItemChanged(quotePositionMap[quote.symbol]!!)
    }
}