package com.james.deliveryproject.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.james.deliveryproject.api.DeliveryService
import com.james.deliveryproject.db.Delivery
import com.james.deliveryproject.db.DeliveryDatabase
import com.james.deliveryproject.db.RemoteKeys
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

private const val START_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediator @Inject constructor(
    private val service: DeliveryService,
    private val database: DeliveryDatabase
): RemoteMediator<Int, Delivery>(){

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Delivery>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            database.remoteKeysDao().remoteById(it.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Delivery>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            database.remoteKeysDao().remoteById(it.id)
        }
    }

    private suspend fun getRemoteKeyForClosetItem(state: PagingState<Int, Delivery>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let {
                database.remoteKeysDao().remoteById(it.id)
            }
        }
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): MediatorResult {
        val page = when(loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyForClosetItem(state)
                remoteKeys?.nextKey?.minus(1) ?: START_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("The remote key should not be null $loadType")
                }

                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }

                if (remoteKeys.preKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }

                remoteKeys.preKey
            }
        }

        try {
            val deliveries = service.getDeliveries(((page - 1) * state.config.pageSize).toLong(), state.config.pageSize)
            val endOfDeliveries = deliveries.isEmpty()

            database.withTransaction {
                val preKey = if (page == START_INDEX) null else page - 1
                val nextKey = if (endOfDeliveries) null else page + 1

                val keys = deliveries.map {
                    RemoteKeys(deliveryId = it.id, preKey = preKey, nextKey = nextKey)
                }

                database.remoteKeysDao().insertAll(keys)
                database.deliveryDao().insertAll(deliveries)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfDeliveries)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}