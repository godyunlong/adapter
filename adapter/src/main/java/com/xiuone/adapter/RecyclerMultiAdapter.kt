package com.xiuone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.lang.Exception

abstract class RecyclerMultiAdapter<T :MultiEntry>() :RecyclerBaseAdapter<T>() {
    private val hashMap = HashMap<Int,@LayoutRes Int>()
    override fun dataType(headPosition: Int, position: Int): Int {
        val item = recyclerDataController.datas[position-headPosition]
        return item.getViewType()
    }

    override fun onDataCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val layoutRes = hashMap[viewType] ?: throw Exception("数据异常，没有相应的type")
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return RecyclerViewViewHolder(view)
    }
}