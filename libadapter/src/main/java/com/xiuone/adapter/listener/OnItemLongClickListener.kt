package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter
import com.xiuone.adapter.adapter.RecyclerViewHolder

interface OnItemLongClickListener<T>{
    fun onItemLongClick(view:View,holder: RecyclerViewHolder<T>)
}