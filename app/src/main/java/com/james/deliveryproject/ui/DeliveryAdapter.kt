package com.james.deliveryproject.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.james.deliveryproject.databinding.DeliveryItemBinding
import com.james.deliveryproject.db.Delivery
import javax.inject.Inject

class DeliveryAdapter @Inject constructor(): PagingDataAdapter<Delivery, DeliveryAdapter.ViewHolder>(DELIVERY_COMPARATOR) {
    companion object {
        private val DELIVERY_COMPARATOR = object : DiffUtil.ItemCallback<Delivery>() {
            override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DeliveryItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
            }
        }

        fun bind(item: Delivery) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}