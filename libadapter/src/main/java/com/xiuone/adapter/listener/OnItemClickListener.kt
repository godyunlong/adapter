package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnItemClickListener{
    fun onItemClick(adapter: RecyclerBaseAdapter<*>, view:View, position:Int)
}