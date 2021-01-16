package com.xiuone.adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class RecyclerSingleAdapter<T>(@LayoutRes private val layoutId:Int) :
    RecyclerBaseAdapter<T>() {
    override fun dataType(headPosition: Int, position: Int): Int = position-headPosition

    override fun onDataCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return RecyclerViewHolder(view)
    }
}