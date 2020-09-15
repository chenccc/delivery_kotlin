package com.james.deliveryproject.ui

import androidx.lifecycle.ViewModel
import com.james.deliveryproject.api.DeliveryService
import javax.inject.Inject

class DeliveryListViewModel @Inject constructor(private val deliveryService: DeliveryService): ViewModel() {
}