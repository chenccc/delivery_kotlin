package com.james.deliveryproject.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "delivery_table")
data class Delivery(
    @PrimaryKey @field:SerializedName("id") val id: String,
    @field:SerializedName("remarks") val remarks: String,
    @field:SerializedName("pickupTime") val pickupTime: String,
    @field:SerializedName("goodsPicture") val goodsPicture: String,
    @field:SerializedName("deliveryFee") val deliveryFee: String,
    @field:SerializedName("surcharge") val surcharge: String,
    @Embedded @field:SerializedName("route") val route: Route,
    @Embedded @field:SerializedName("sender") val sender: Sender
)

data class Route(
    @field:SerializedName("start") val start: String,
    @field:SerializedName("end") val end: String
)

data class Sender(
    @field:SerializedName("phone") val phone:String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("email") val email: String
)