package com.james.deliveryproject.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys (
    @PrimaryKey val deliveryId: String,
    val preKey: Int?,
    val nextKey: Int?
)