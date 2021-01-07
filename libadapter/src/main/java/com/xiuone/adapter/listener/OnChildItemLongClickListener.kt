package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnChildItemLongClickListener{
    fun onItemChildLongClick(adapter: RecyclerBaseAdapter<*>, view:View, position:Int)
}