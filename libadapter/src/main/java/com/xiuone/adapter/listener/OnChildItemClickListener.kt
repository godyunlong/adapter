package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnChildItemClickListener<T>{
    fun onItemChildClick(adapter: RecyclerBaseAdapter<T> ,view:View,position:Int)
}