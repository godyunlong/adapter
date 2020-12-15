package com.xiuone.adapter.controller

import android.view.View

class RefreshLoadController {
    var loadView:View?=null
    var refreshView:View?=null
    private val IDIE = 1//正常状态
    private val REFRSH_ING = 2//刷新状态
    private val LOADMORE_ING = 3//加载更多状态
    private var STATUS = IDIE

    /**
     * 控制刷新状态
     */
    fun setRefreshStatus(status:Boolean){
        if (status && STATUS == IDIE && refreshView != null)
            STATUS = REFRSH_ING
        else if (STATUS != LOADMORE_ING)
            STATUS = IDIE
    }

    /**
     * 控制加载更多状态
     */
    fun setLoadMoreStatus(status: Boolean){
        if (status && STATUS == IDIE && loadView!= null)
            STATUS = LOADMORE_ING
        else if (STATUS != REFRSH_ING)
            STATUS = IDIE
    }

    /**
     * 获取数量
     */
    fun getDataSize() :Int = if (STATUS != IDIE) 1 else 0

    /**
     * 是否是正在加载更多
     */
    fun isLoad():Boolean = STATUS == LOADMORE_ING

    /**
     * 是否正在刷新
     */
    fun isRefresh():Boolean = STATUS == REFRSH_ING

    fun refreshCount():Int = if (STATUS == REFRSH_ING) 1 else 0

    fun loadMoreCount():Int = if (STATUS == LOADMORE_ING) 1 else 0
}