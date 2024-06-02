package com.mertinam.storeapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id_ :Int  =1,
    @ColumnInfo(name = "base_id")
    @SerializedName("id")
    val id: Int,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title : String,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price : Double,
    @ColumnInfo(name = "category")
    @SerializedName("category")
    val category : String,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description : String,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val imageUrl : String

) : Parcelable