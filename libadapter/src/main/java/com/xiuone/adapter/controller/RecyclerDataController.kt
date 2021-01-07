package com.xiuone.adapter.controller

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

/**
 * 用于控制recyclerView的数量
 */
class RecyclerDataController<T>(adapter: RecyclerBaseAdapter<T>) :RecyclerHeadFootController<T>(adapter){
    val datas = ArrayList<T>()
    var entryView:View?=null
    private var init = false//刚刚初始化

    /**
     * 获取总共有多少数据
     */
    override fun getDataSize():Int{
        if (datas.size>0)return datas.size
        if (init && entryView != null)return 1
        return  0
    }

    fun setData(data:MutableList<T>?,isNew: Boolean){
        if (data == null)return
        if (isNew)
            this.datas.clear()
        this.datas.addAll(data)
        init = true
    }

    fun setNewData(data:MutableList<T>?){
        if (data == null)return
        this.datas.clear()
        this.datas.addAll(data)
        init = true
        adapter.notifyDataSetChanged()
    }

    fun addData(data: MutableList<T>?){
        if (data == null)return
        datas.addAll(data)
        if (data.size == datas.size)
            adapter.notifyDataSetChanged()
        else
            adapter.notifyItemChanged(adapter.itemCount-data.size,data.size)
    }

    fun addData(position:Int,item: T?){
        if (item == null)return
        if (position in 0 until datas.size)
            datas.add(position, item)
        else
            datas.add(item)
        if (datas.size == 1)
            adapter.notifyDataSetChanged()
        else{
            adapter.notifyItemChanged(position)
        }
    }

    fun addItem(item: T){
        addData(getItemCount(),item)
    }

    fun remove(position: Int){
        if (position < datas.size){
            datas.removeAt(position)
            adapter.notifyItemRemoved(position+getHeadSize())
        }
    }

    fun getItem(position: Int) : T?= if (position < datas.size) datas[position] else null

}