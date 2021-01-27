package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnItemLongClickListener<T>{
    fun onItemLongClick(adapter: RecyclerBaseAdapter<T>, view:View, position:Int)
}