package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter
import com.xiuone.adapter.adapter.RecyclerViewHolder

interface OnChildItemLongClickListener<T>{
    fun onItemChildLongClick(view:View, holder: RecyclerViewHolder<T>)
}