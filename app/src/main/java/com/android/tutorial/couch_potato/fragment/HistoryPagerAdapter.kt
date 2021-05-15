package com.android.tutorial.couch_potato.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.tutorial.couch_potato.R

class HistoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val tabTitles = listOf<Int>(
        R.drawable.ic_bookmark,
        R.drawable.ic_favorite
    )

    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment {
        return if(position == 0){
            HistoryBookmarkFragment()
        }else{
            HistoryFavoriteFragment()
        }
    }
}