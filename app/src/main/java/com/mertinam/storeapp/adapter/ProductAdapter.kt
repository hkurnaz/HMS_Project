package com.mertinam.storeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.mertinam.storeapp.R
import com.mertinam.storeapp.databinding.ItemProductBinding
import com.mertinam.storeapp.model.Product
import com.mertinam.storeapp.view.MainActivity
import com.mertinam.storeapp.view.home.HomeFragment


class ProductAdapter(var productList : ArrayList<Product> , private var onClick: (position : Int )-> Unit) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(var view : ItemProductBinding) : RecyclerView.ViewHolder(view.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemProductBinding>(inflater, R.layout.item_product,parent,false)

        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
       return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.view.ProductTitleTV.text=productList[position].title
        holder.view.cvItem.setOnClickListener{
            if(HomeFragment.interstitialAd!= null && HomeFragment.interstitialAd!!.isLoaded){
                HomeFragment.interstitialAd!!.show(MainActivity.activity)
            }
            else {
                Toast.makeText(holder.view.root.context, "Ad did not load", Toast.LENGTH_SHORT).show();
            }
            onClick(position)
        }
        Glide.with(holder.view.root).load(productList[position].imageUrl).into(holder.view.ProductIV)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList : List<Product>)
    {
        productList = updatedList as ArrayList<Product>
        notifyDataSetChanged()
    }

}