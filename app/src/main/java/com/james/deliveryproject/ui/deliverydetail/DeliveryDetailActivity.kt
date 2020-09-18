package com.james.deliveryproject.ui.deliverydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.james.deliveryproject.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryDetailActivity : AppCompatActivity() {
    @Inject lateinit var viewModel: DeliveryDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_detail)
    }
}