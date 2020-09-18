package com.james.deliveryproject.ui.adapter
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.james.deliveryproject.db.Delivery
import com.james.deliveryproject.ui.viewholder.DeliveryViewHolder
import javax.inject.Inject

class DeliveryAdapter @Inject constructor(): PagingDataAdapter<Delivery, RecyclerView.ViewHolder>(
    DELIVERY_COMPARATOR
) {
    companion object {
        private val DELIVERY_COMPARATOR = object : DiffUtil.ItemCallback<Delivery>() {
            override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as DeliveryViewHolder).bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DeliveryViewHolder.create(
            parent
        )
    }
}