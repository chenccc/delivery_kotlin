package com.james.deliveryproject.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Delivery::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class DeliveryDatabase: RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}