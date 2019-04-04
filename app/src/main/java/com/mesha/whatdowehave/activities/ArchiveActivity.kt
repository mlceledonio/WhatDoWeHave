package com.mesha.whatdowehave.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ItemListRVAdapter
import com.mesha.whatdowehave.classes.SwipeDelete
import com.mesha.whatdowehave.models.ItemModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ArchiveActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    var itemList:ArrayList<ItemModel> = arrayListOf()
    var adapter = ItemListRVAdapter(itemList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("ArchiveDebug", "Entered onCreate Archive")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)

        try{
            Log.d("ArchiveDebug", "Entered try phrase")
            val dateToday = SimpleDateFormat("dd/MM/yyyy").format(Date())
            Log.d("ArchiveDebug", dateToday.toString())
            val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlSelectName = "SELECT item_id, item_name, quantity, expiration FROM item WHERE date(expiration) < date('$dateToday')"
            Log.d("ArchiveDebug", sqlSelectName)
            val cursor = myDatabase.rawQuery(sqlSelectName, null)
            val itemIdIx = cursor.getColumnIndex("item_id")
            val itemNameIx = cursor.getColumnIndex("item_name")
            val qtyIx = cursor.getColumnIndex("quantity")
            val expIx = cursor.getColumnIndex("expiration")

            cursor.moveToFirst()
            while (cursor != null){
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

        recyclerView = findViewById(R.id.archive_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val swipeHandler = object : SwipeDelete(this){
            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                adapter.removeAt(p0.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }
}
