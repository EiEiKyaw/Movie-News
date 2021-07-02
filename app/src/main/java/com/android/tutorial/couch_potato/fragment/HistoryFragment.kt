package com.android.tutorial.couch_potato.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.adapter.HistoryPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.frag_history.view.*

class HistoryFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_history, container, false)

        val historyPagerAdapter = HistoryPageAdapter(this)
        view.vpHistory.adapter = historyPagerAdapter

        TabLayoutMediator(view.tlHistory, view.vpHistory) { tab, position ->
            tab.setIcon(historyPagerAdapter.tabTitles[position])
        }.attach()

        return view
    }
}