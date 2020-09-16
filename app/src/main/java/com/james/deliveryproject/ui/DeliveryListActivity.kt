package com.james.deliveryproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.james.deliveryproject.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DeliveryListActivity : AppCompatActivity() {
    @Inject lateinit var viewModel: DeliveryListViewModel
    private val adapter: DeliveryAdapter = DeliveryAdapter()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        delivery_list.addItemDecoration(decoration)

        initAdapter()
        getDeliveries()
    }

    private fun initAdapter() {

    }

    private fun getDeliveries() {
        lifecycleScope.launch {
            viewModel.getDeliveries().collect {
                adapter.submitData(it)
            }
        }
    }
}