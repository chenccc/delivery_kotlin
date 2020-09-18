package com.james.deliveryproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.james.deliveryproject.R
import com.james.deliveryproject.db.Delivery
import com.james.deliveryproject.util.Util

class DeliveryViewHolder(private val view: View) :  RecyclerView.ViewHolder(view){
    private val deliveryImage:ImageView = view.findViewById(R.id.deliveryImage)
    private val fromText: TextView = view.findViewById(R.id.from_text)
    private val toText: TextView = view.findViewById(R.id.to_text)
    fun bind(item: Delivery) {
        Glide.with(view.context).load(item.goodsPicture)
            .centerCrop()
            .placeholder(R.mipmap.placeholder)
            .into(deliveryImage)
        val fromString = Util.FROM + item.route.start
        val toString = Util.TO + item.route.end

        fromText.text = fromString
        toText.text = toString
    }
    companion object {
        fun create(parent: ViewGroup): DeliveryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.delivery_item, parent, false)
            return DeliveryViewHolder(view)
        }
    }
}