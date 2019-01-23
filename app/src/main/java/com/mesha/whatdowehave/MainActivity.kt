package com.mesha.whatdowehave

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById<ListView>(R.id.items_list_view)
        var itemList = listOf("corned beef", "century tuna", "maling");

        val adapter = ArrayAdapter(this, R.layout.simple_item_list, itemList)
        listView.adapter = adapter

    }
}
