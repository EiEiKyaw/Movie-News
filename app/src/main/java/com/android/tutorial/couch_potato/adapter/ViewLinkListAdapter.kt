package com.android.tutorial.couch_potato.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tutorial.couch_potato.R
import com.android.tutorial.couch_potato.activity.MovieDetailActivity
import com.android.tutorial.couch_potato.model.ViewLink
import kotlinx.android.synthetic.main.item_view_link.view.*

class ViewLinkListAdapter : RecyclerView.Adapter<ViewLinkListAdapter.MyViewHolder>() {

    private val linkList = mutableListOf<ViewLink>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_link, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val link = linkList[position]
        holder.itemView.tvViewLink.text = link.url
    }

    override fun getItemCount(): Int {
        return linkList.size
    }

    fun setNewDataList(list: List<ViewLink>) {
        linkList.clear()
        linkList.addAll(list)
        notifyDataSetChanged()
    }

    fun setNewData(link: ViewLink) {
        linkList.add(link)
        notifyItemInserted(linkList.size - 1)
    }

}