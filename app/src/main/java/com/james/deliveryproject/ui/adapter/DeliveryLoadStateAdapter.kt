package com.james.deliveryproject.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.james.deliveryproject.ui.viewholder.DeliveryLoadStateViewHolder

class DeliveryLoadStateAdapter (
    private val retry: () -> Unit
): LoadStateAdapter<DeliveryLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: DeliveryLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DeliveryLoadStateViewHolder {
        return DeliveryLoadStateViewHolder.create(parent, retry)
    }
}