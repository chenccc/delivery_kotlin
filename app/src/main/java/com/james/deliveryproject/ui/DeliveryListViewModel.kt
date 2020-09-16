package com.james.deliveryproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.james.deliveryproject.data.DeliveryRepository
import com.james.deliveryproject.db.Delivery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DeliveryListViewModel @Inject constructor(private val repository: DeliveryRepository): ViewModel() {
    fun getDeliveries(): Flow<PagingData<Delivery>> {
        return repository.getDeliveriesStream().cachedIn(viewModelScope)
    }
}