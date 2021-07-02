package com.android.tutorial.couch_potato.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected val itemList = mutableListOf<T>()

    protected fun getLayoutInflator(view: ViewGroup) = LayoutInflater.from(view.context)

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setNewDataList(newItems: List<T>) {
        itemList.clear()
        itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setNewData(newItem: T) {
        if (!itemList.contains(newItem))
            itemList.add(newItem)
        notifyItemInserted(itemList.size - 1)
    }

    fun getItemAt(position: Int): T {
        return itemList[position]
    }
}