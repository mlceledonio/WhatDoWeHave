package com.mesha.whatdowehave.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
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

    fun removeAt(position: Int){
        val myDatabase = context.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
        val sqlDelete = "DELETE FROM item WHERE item_name = '" + items[position].itemName + "' AND quantity = " + items[position].quantity + " AND expiration = '" + items[position].expiration + "'"
        Log.d("SQL_DELETE", "item_name " + items[position].itemName)
        Log.d("SQL_DELETE", "quantity " + items[position].quantity)
        Log.d("SQL_DELETE", "expiration " + items[position].expiration)
        Log.d("SQL_DELETE", sqlDelete)
        val sqlSelect = "SELECT * FROM item WHERE item_name = '" + items[position].itemName + "' AND quantity = " + items[position].quantity + " AND expiration = '" + items[position].expiration + "'"
        items.removeAt(position)
        var delRes = myDatabase.execSQL(sqlDelete)
        var selRes = myDatabase.execSQL(sqlSelect)
        Log.d("SQL_DELETE", delRes.toString())
        Log.d("SQL_DELETE", selRes.toString())
        notifyItemRemoved(position)
    }
}

