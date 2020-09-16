package com.james.deliveryproject.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.james.deliveryproject.db.Delivery
import com.james.deliveryproject.db.DeliveryDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeliveryRepository@Inject constructor(private val remoteMediator: DeliveryRemoteMediator,
    private val database: DeliveryDatabase) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }

    fun getDeliveriesStream(): Flow<PagingData<Delivery>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders =  false
            ),
            pagingSourceFactory = {database.deliveryDao().getDeliveries()},
            remoteMediator = remoteMediator
        ).flow
    }

}