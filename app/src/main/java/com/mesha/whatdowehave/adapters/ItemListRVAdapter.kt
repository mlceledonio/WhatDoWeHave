package com.mesha.whatdowehave.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.models.ItemModel
import kotlinx.android.synthetic.main.column_item_list.view.*

class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
    val txtItemName = view.tv_listItem
    val txtQuantity = view.tv_listQty
    val txtExpiration = view.tv_listExp
}

class ItemListRVAdapter(val items: ArrayList<ItemModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.column_item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.txtItemName?.text = items[position].itemName
        holder?.txtQuantity.text = (items[position].quantity).toString()
        holder?.txtExpiration.text = items[position].expiration
    }
}

