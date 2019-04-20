package com.mesha.whatdowehave.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mesha.whatdowehave.R
import com.mesha.whatdowehave.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {

    lateinit var fragmentAdapter:ViewPagerAdapter
    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000
    private var mNotified = false

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

        /*if(!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this@MainActivity)
        }*/
    }

    private fun addItem() {
        val intent = Intent(applicationContext, AddItemActivity::class.java)
        startActivity(intent)
    }

}
