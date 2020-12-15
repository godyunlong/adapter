package com.xiuone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

abstract class RecyclerSingleAdapter<T>(@LayoutRes private val layoutId:Int) :RecyclerBaseAdapter<T>() {
    override fun dataType(headPosition: Int, position: Int): Int = position

    override fun onDataCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return RecyclerViewViewHolder(view)
    }
}