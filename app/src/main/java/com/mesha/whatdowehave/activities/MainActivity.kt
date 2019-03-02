package com.mesha.whatdowehave.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ItemListAdapter
import com.mesha.whatdowehave.models.ItemModel
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    var itemList:ArrayList<ItemModel> = arrayListOf()
    var adapter = ItemListAdapter(this, itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.items_list_view)

        listView.adapter = adapter
        //val adapter = ArrayAdapter(this, R.layout.simple_item_list, itemList)
        //listView.adapter = adapter

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
