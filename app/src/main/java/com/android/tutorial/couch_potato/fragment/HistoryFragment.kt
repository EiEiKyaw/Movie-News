package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.tutorial.couch_potato.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.frag_history.view.*

class HistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history, container, false)

        val historyPagerAdapter = HistoryPagerAdapter(this)
        view.vpHistory.adapter = historyPagerAdapter

        TabLayoutMediator(view.tlHistory, view.vpHistory) { tab, position ->
            tab.setIcon(historyPagerAdapter.tabTitles[position])
        }.attach()

        return view
    }
}