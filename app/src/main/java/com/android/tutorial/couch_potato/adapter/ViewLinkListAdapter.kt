package com.android.tutorial.couch_potato.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.model.ViewLink
import kotlinx.android.synthetic.main.item_view_link.view.*

class ViewLinkListAdapter : BaseAdapter<ViewLink, ViewLinkListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            getLayoutInflator(parent).inflate(R.layout.item_view_link, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val link = itemList[position]
        holder.itemView.tvViewLink.text = link.url
    }

}