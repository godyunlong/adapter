package com.xiuone.adapter.controller

import android.view.View

/**
 * 用于控制recyclerView的数量
 */
class RecyclerDataController<T> {
    val datas = ArrayList<T>()
    val heads = ArrayList<View>()
    val foots = ArrayList<View>()
    var entryView:View?=null
    var showHeadIntEntry = false//是否在空数据的时候显示head
    var showFootIntEntry = false//是否在空数据的时候显示foot
    var showEntryIntStart = false//是否在刚进入的时候就直接显示空数据
    var showPreloading = false//是否在显示
    var preloadView:View?= null//预加载view
    private var init = false//刚刚初始化

    /**
     * 获取item总数
     */
    fun getItemCount():Int{
        return getHeadSize()+getFootSize()+getDataSize()
    }

    /**
     * 获取显示的headSize
     */
    fun getHeadSize():Int{
        if (datas.isNotEmpty() && !showHeadIntEntry)
            return 0
        else
            return heads.size
    }

    /**
     * 获取显示的footView
     */
    fun getFootSize():Int{
        if (datas.isNotEmpty() && !showFootIntEntry)
            return 0
        else
            return foots.size
    }

    /**
     * 获取总共有多少数据
     */
    fun getDataSize():Int{
        if (datas.size>0)return datas.size
        if ((init || showEntryIntStart)&& entryView != null)return 1
        if (!init && preloadView != null && showPreloading) return 1
        return  0
    }
}