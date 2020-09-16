package com.james.deliveryproject.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DeliveryDao {
    @Query("Select * from delivery_table")
    fun getDeliveries(): PagingSource<Int, Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deliveries: List<Delivery>)
}