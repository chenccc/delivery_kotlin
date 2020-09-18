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
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { delivery ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao().remoteById(delivery.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Delivery>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { delivery ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDao().remoteById(delivery.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Delivery>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { deliveryId ->
                database.remoteKeysDao().remoteById(deliveryId)
            }
        }
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: START_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    return MediatorResult.Error(InvalidObjectException("Remote key and the prevKey should not be null"))
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.preKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.preKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    return MediatorResult.Error( InvalidObjectException("Remote key should not be null for $loadType"))
                }
                remoteKeys.nextKey
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