package com.xiuone.adapter.listener

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

interface OnChildItemClickListener{
    fun onItemChildClick(adapter: RecyclerBaseAdapter<*> ,view:View,position:Int)
}