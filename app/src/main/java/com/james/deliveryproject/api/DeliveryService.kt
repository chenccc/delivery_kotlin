package com.james.deliveryproject.api

import com.james.deliveryproject.db.Delivery
import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveryService {
    /**
     * Get deliveries
     */
    @GET("v2/deliveries")
    suspend fun getDeliveries(
        @Query("offset") offset: Long,
        @Query("limit") limit: Int
    ): List<Delivery>
}