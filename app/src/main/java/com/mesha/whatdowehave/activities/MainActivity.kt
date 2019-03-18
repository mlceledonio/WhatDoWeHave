package com.mesha.whatdowehave.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.ListView
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ItemListRVAdapter
import com.mesha.whatdowehave.classes.SwipeDelete
import com.mesha.whatdowehave.models.ItemModel
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var recyclerView: RecyclerView
    var itemList:ArrayList<ItemModel> = arrayListOf()
    var adapter = ItemListRVAdapter(itemList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        try{

            val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlCreate = "CREATE TABLE IF NOT EXISTS item (item_name VARCHAR, quantity INT, expiration DATE)"
            myDatabase.execSQL(sqlCreate)
            val sqlSelectName = "SELECT * FROM item"
            val cursor = myDatabase.rawQuery(sqlSelectName, null)
            val itemNameIx = cursor.getColumnIndex("item_name")
            val qtyIx = cursor.getColumnIndex("quantity")
            val expIx = cursor.getColumnIndex("expiration")


            cursor.moveToFirst()
            while (cursor != null){
                var tItem = ItemModel(cursor.getString(itemNameIx), cursor.getInt(qtyIx), cursor.getString(expIx))
                itemList.add(tItem)

                cursor.moveToNext()
                adapter.notifyDataSetChanged()
            }
            cursor?.close()
            myDatabase.close()

        }catch(e: Exception){
            e.printStackTrace()
        }

        recyclerView = findViewById(R.id.list_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val swipeHandler = object : SwipeDelete(this){
            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                adapter.removeAt(p0.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener{
            addItem()
        }
    }

    private fun addItem() {
        val intent = Intent(applicationContext, AddItemActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
        val sqlSelectName = "SELECT * FROM item"
        val cursor = myDatabase.rawQuery(sqlSelectName, null)
        val itemNameIx = cursor.getColumnIndex("item_name")
        val qtyIx = cursor.getColumnIndex("quantity")
        val expIx = cursor.getColumnIndex("expiration")

        cursor.moveToFirst()

        itemList.clear()

        while (cursor != null && !cursor.isAfterLast){
            var tItem = ItemModel(cursor.getString(itemNameIx), cursor.getInt(qtyIx), cursor.getString(expIx))
            itemList.add(tItem)

            cursor.moveToNext()
            adapter.notifyDataSetChanged()
        }
        cursor?.close()
        myDatabase.close()

    }

}
