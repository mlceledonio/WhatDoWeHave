package com.mesha.whatdowehave.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ItemListRVAdapter
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
            val dateToday = SimpleDateFormat("yyyy-MM-dd").format(Date())
            Log.d("ArchiveDebug", dateToday.toString())
            val myDatabase = this.openOrCreateDatabase("item_list", Context.MODE_PRIVATE, null)
            val sqlSelectName = "SELECT item_id, item_name, quantity, expiration FROM item WHERE quantity = 0"
            Log.d("ArchiveDebug", sqlSelectName)
            val cursor = myDatabase.rawQuery(sqlSelectName, null)
            val itemIdIx = cursor.getColumnIndex("item_id")
            val itemNameIx = cursor.getColumnIndex("item_name")
            val qtyIx = cursor.getColumnIndex("quantity")
            val expIx = cursor.getColumnIndex("expiration")

            cursor.moveToFirst()

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
                val updateIntent = Intent(applicationContext, UpdateItemActivity::class.java)
                updateIntent.putExtra("KEY_ITEM_NAME", itemList.get(pos).itemName)
                updateIntent.putExtra("KEY_QUANTITY", itemList.get(pos).quantity)
                updateIntent.putExtra("KEY_EXPIRATION", itemList.get(pos).expiration)
                startActivityForResult(updateIntent, 2)
            }

            override fun onDeleteClick(pos: Int, view: View) {
                adapter.removeAt(pos)
            }
        })

        recyclerView = findViewById(R.id.archive_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_archive, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_main -> {
                showMain()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMain(){
        Log.d("ArchiveDebug", "Show main")
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
