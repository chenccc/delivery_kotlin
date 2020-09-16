package com.james.deliveryproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("Select * from remote_keys where deliveryId = :deliveryId")
    suspend fun remoteById(deliveryId: String): RemoteKeys
}