package com.james.deliveryproject.ui.deliverylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.james.deliveryproject.R
import com.james.deliveryproject.ui.adapter.DeliveryAdapter
import com.james.deliveryproject.ui.adapter.DeliveryLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_delivery_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DeliveryListActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: DeliveryListViewModel
    @Inject lateinit var adapter: DeliveryAdapter

    private fun search() {
        lifecycleScope.launch {
            viewModel.getDeliveries().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_list)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)

        initAdapter()
        search()
    }

    private fun initAdapter() {
        list.adapter = adapter.withLoadStateFooter(
            footer = DeliveryLoadStateAdapter{adapter.retry()}
        )

        adapter.addLoadStateListener { loadState ->
            list.isVisible = loadState.source.refresh is LoadState.NotLoading
            progress_bar.isVisible = loadState.source.refresh is LoadState.Loading
            retry_button.isVisible = loadState.source.refresh is LoadState.Error
        }
    }
}