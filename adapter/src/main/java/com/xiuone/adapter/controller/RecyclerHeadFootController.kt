package com.xiuone.adapter.controller

import android.view.View
import com.xiuone.adapter.adapter.RecyclerBaseAdapter

/**
 * 用于控制recyclerView的数量
 */
open abstract class RecyclerHeadFootController<T>(val adapter: RecyclerBaseAdapter<T>) {
    val heads = ArrayList<View>()
    val foots = ArrayList<View>()

    /**
     * 获取item总数
     */
    open fun getItemCount():Int{
        return getHeadSize()+getFootSize()+getDataSize()
    }

    /**
     * 获取显示的headSize
     */
    fun getHeadSize():Int = heads.size

    /**
     * 获取显示的footView
     */
    fun getFootSize():Int = foots.size

    /**
     * 添加head 或者foot
     */
    fun addView(view: View,head:Boolean){
        if (head) {
            heads.add(view)
            adapter.notifyItemInserted(heads.size)
        }else{
            foots.add(view)
            adapter.notifyItemChanged(getItemCount())
        }
    }

    /**
     * 指定位置添加head或者foot
     */
    fun addView(position: Int,view: View,head: Boolean){
        if (position < heads.size && position>=0 && head) {
            heads.add(position, view)
            adapter.notifyItemInserted(position)
        }else if (position < foots.size && position >=0 && !head){
            foots.add(position,view)
            adapter.notifyItemInserted(position+getHeadSize()+getDataSize())
        }else addView(view,head)
    }

    /**
     * 移除head 或者foot
     */
    fun removeView(position:Int,head: Boolean){
        if (position < heads.size && head){
            heads.removeAt(position)
            adapter.notifyItemRemoved(position)
        }else if (position < foots.size && !head){
            foots.removeAt(position)
            adapter.notifyItemRemoved(position+getHeadSize())
        }
    }


    abstract fun getDataSize():Int
}