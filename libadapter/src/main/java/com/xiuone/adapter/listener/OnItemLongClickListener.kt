package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnItemLongClickListener{
    fun onItemLongClick(adapter: RecyclerBaseAdapter<*>, view:View, position:Int)
}