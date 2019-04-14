package com.mesha.whatdowehave.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fragmentAdapter:ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener{
            addItem()
        }
    }

    private fun addItem() {
        val intent = Intent(applicationContext, AddItemActivity::class.java)
        startActivity(intent)
    }

}
