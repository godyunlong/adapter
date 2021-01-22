package com.xiuone.adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.xiuone.adapter.MultiEntry
import java.lang.Exception

abstract class RecyclerMultiAdapter<T : MultiEntry>() :
    RecyclerBaseAdapter<T>() {
    val hashMap = HashMap<Int,@LayoutRes Int>()
    override fun dataType(headPosition: Int, position: Int): Int {
        val item = dataController.datas[position-headPosition]
        return item.getViewType()
    }

    override fun onDataCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutRes = hashMap[viewType] ?: throw Exception("数据异常，没有相应的type")
        val view = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return RecyclerViewHolder(view)
    }
}