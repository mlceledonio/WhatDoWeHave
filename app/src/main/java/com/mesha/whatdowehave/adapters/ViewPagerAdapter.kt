package com.mesha.whatdowehave.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mesha.whatdowehave.activities.MainActivity
import com.mesha.whatdowehave.fragments.ArchiveFragment
import com.mesha.whatdowehave.fragments.InUseFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter (fm){

    override fun getItem(position: Int): Fragment {
        if(position == 0){
            var inUseFragment = InUseFragment()

            return inUseFragment
        } else {
            return  ArchiveFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0){
            return "In Use"
        } else {
            return "Archive"
        }
    }
}