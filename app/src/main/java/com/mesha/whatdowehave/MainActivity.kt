package com.mesha.whatdowehave

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    var itemList:MutableList<String> = mutableListOf<String>("corned beef", "maling", "century tuna")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        listView = findViewById<ListView>(R.id.items_list_view)


        val adapter = ArrayAdapter(this, R.layout.simple_item_list, itemList)
        listView.adapter = adapter

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener{
            addItem()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val newItemName: String = data!!.getStringExtra("newItem")
        if(!newItemName.isEmpty()){
            itemList.add(newItemName)
        }
        listView = findViewById<ListView>(R.id.items_list_view)
        listView.invalidateViews()
    }

    private fun addItem() {
        val intent = Intent(applicationContext, AddItemActivity::class.java)
        startActivityForResult(intent, 1)
    }

}
