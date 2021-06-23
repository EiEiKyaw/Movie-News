package com.android.tutorial.couch_potato.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.tutorial.couch_potato.fragment.CategoryFragment
import com.android.tutorial.couch_potato.fragment.HistoryFragment
import com.android.tutorial.couch_potato.fragment.MovieListFragment
import com.android.tutorial.couch_potato.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botNav.setOnNavigationItemSelectedListener {item ->
            val fragment : Fragment? = when(item.itemId){
                R.id.menu_item_latest -> MovieListFragment()
                R.id.menu_item_history -> HistoryFragment()
                else -> null
            }

            fragment?.let {
                changeFragment(it)
            }

            true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.containerContent, fragment).commit()
    }
}