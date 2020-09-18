package com.james.deliveryproject.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.james.deliveryproject.R
import com.james.deliveryproject.databinding.DeliveryLoadStateFooterViewItemBinding

class DeliveryLoadStateViewHolder(
    private val binding :DeliveryLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.retryBtn.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryBtn.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): DeliveryLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.delivery_load_state_footer_view_item, parent, false)
            val binding = DeliveryLoadStateFooterViewItemBinding.bind(view)
            return DeliveryLoadStateViewHolder(binding, retry)
        }
    }
}