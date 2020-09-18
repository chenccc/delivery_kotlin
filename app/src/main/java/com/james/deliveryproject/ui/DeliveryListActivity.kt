package com.james.deliveryproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.james.deliveryproject.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_delivery_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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
        list.adapter = adapter
    }
}