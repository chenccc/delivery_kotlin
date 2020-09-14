package com.james.deliveryproject.delivery_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.james.deliveryproject.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryListActivity : AppCompatActivity() {
    @Inject lateinit var viewModel: DeliveryListViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}