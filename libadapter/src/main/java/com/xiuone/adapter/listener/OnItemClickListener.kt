package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter
import com.xiuone.adapter.adapter.RecyclerViewHolder

interface OnItemClickListener<T>{
    fun onItemClick(view:View, holder: RecyclerViewHolder<T>)
}