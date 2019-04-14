package com.mesha.whatdowehave.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.activities.UpdateItemActivity
import com.mesha.whatdowehave.adapters.ItemListRVAdapter
import com.mesha.whatdowehave.models.ItemModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ArchiveFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    var itemList:ArrayList<ItemModel> = arrayListOf()
    lateinit var adapter: ItemListRVAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = ItemListRVAdapter(itemList, context)
    }

    override fun onResume() {
        super.onResume()
        val myDatabase = activity!!.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
        val sqlSelectName = "SELECT item_id, item_name, quantity, expiration FROM item WHERE quantity = 0"
        val cursor = myDatabase.rawQuery(sqlSelectName, null)
        val itemIdIx = cursor.getColumnIndex("item_id")
        val itemNameIx = cursor.getColumnIndex("item_name")
        val qtyIx = cursor.getColumnIndex("quantity")
        val expIx = cursor.getColumnIndex("expiration")

        cursor.moveToFirst()

        if(cursor.count == 0){
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
        else{
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        itemList.clear()

        while (cursor != null && !cursor.isAfterLast){
            var tItem = ItemModel(cursor.getInt(itemIdIx), cursor.getString(itemNameIx), cursor.getInt(qtyIx), cursor.getString(expIx))
            itemList.add(tItem)

            cursor.moveToNext()
            Log.d("ColorDebug", "Main: beforeNotify")
            adapter.notifyDataSetChanged()
            Log.d("ColorDebug", "Main: afterNotify")
        }
        cursor?.close()
        myDatabase.close()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View=inflater!!.inflate(R.layout.fragment_archive, container, false)

        var itemCount = 0

        try{
            Log.d("ArchiveDebug", "Entered try phrase")
            val dateToday = SimpleDateFormat("yyyy-MM-dd").format(Date())
            Log.d("ArchiveDebug", dateToday.toString())
            val myDatabase = activity!!.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlSelectName = "SELECT item_id, item_name, quantity, expiration FROM item WHERE quantity = 0"
            Log.d("ArchiveDebug", sqlSelectName)
            val cursor = myDatabase.rawQuery(sqlSelectName, null)
            val itemIdIx = cursor.getColumnIndex("item_id")
            val itemNameIx = cursor.getColumnIndex("item_name")
            val qtyIx = cursor.getColumnIndex("quantity")
            val expIx = cursor.getColumnIndex("expiration")

            cursor.moveToFirst()
            itemCount = cursor.count

            while (cursor != null && !cursor.isAfterLast){
                var tItem = ItemModel(cursor.getInt(itemIdIx) ,cursor.getString(itemNameIx), cursor.getInt(qtyIx), cursor.getString(expIx))
                itemList.add(tItem)

                cursor.moveToNext()
                adapter.notifyDataSetChanged()
            }
            cursor?.close()
            myDatabase.close()
        }catch(e: Exception){
            Log.d("ArchiveDebug", "Entered catch")
            Log.e("ArchiveError", e.toString())
            e.printStackTrace()

        }

        adapter.setOnItemClickListener(object : ItemListRVAdapter.ItemClickListener{
            override fun onItemClick(pos: Int, view: View) {

                var itemExpanded: Boolean = itemList.get(pos).isExpanded()
                itemList.get(pos).expanded = !itemExpanded
                adapter.notifyItemChanged(pos)
            }

            override fun onEditClick(pos: Int, view: View) {
                val updateIntent = Intent(activity!!.applicationContext, UpdateItemActivity::class.java)
                updateIntent.putExtra("KEY_ITEM_NAME", itemList.get(pos).itemName)
                updateIntent.putExtra("KEY_QUANTITY", itemList.get(pos).quantity)
                updateIntent.putExtra("KEY_EXPIRATION", itemList.get(pos).expiration)
                startActivity(updateIntent)
            }

            override fun onDeleteClick(pos: Int, view: View) {
                adapter.removeAt(pos)
            }
        })

        emptyView = view.findViewById(R.id.tv_empty_view)
        recyclerView = view.findViewById(R.id.archive_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        if(itemCount == 0){
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
        else{
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }

        return view
    }

    companion object {
        fun newInstance(): ArchiveFragment = ArchiveFragment()
    }


}
