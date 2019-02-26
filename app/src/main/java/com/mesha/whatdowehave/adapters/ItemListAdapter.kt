package com.mesha.whatdowehave.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.models.ItemModel
import kotlinx.android.synthetic.main.column_item_list.view.*

class ItemListAdapter(private var activity: Activity, private var items: ArrayList<ItemModel>): BaseAdapter(){

    private class ViewHolder(row: View?){
        var txtItemName: TextView? = null
        var txtQuantity: TextView? = null
        var txtExpiration: TextView? = null

        init {
            this.txtItemName = row?.findViewById(R.id.tv_listItem)
            this.txtQuantity = row?.findViewById(R.id.tv_listQty)
            this.txtExpiration = row?.findViewById(R.id.tv_listExp)
        }
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if(convertView == null){
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.column_item_list, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var itemModel = items[position]
        viewHolder.txtItemName?.text = itemModel.itemName
        viewHolder.txtQuantity?.text = itemModel.quantity.toString()
        viewHolder.txtExpiration?.text = itemModel.expiration

        return view as View
    }

}