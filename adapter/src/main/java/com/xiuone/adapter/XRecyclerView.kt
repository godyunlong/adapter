package com.xiuone.adapter

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class XRecyclerView (context: Context,attributeSet: AttributeSet?=null,defStyleAttr :Int= R.attr.recyclerViewStyle)
    :RecyclerView(context,attributeSet,defStyleAttr) {
    private var xAdapter:RecyclerBaseAdapter<*> ?= null
    private var canRefresh = false//是否可以刷新
    private var canLoadMore = false//是否可以加载更多

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        if (adapter != null && adapter is RecyclerBaseAdapter<*>){
            xAdapter = adapter
            val refreshView = LayoutInflater.from(context).inflate(R.layout.refesh_view,null)
            changeView(refreshView)
            xAdapter?.refreshLoadController?.refreshView = refreshView

            val loadView = LayoutInflater.from(context).inflate(R.layout.loadmore_view,null)
            changeView(loadView)
            xAdapter?.refreshLoadController?.loadView = loadView

        }else null
    }

    /**
     * 设置是否可以加载更多
     */
    fun setLoadMore(status:Boolean){
        canLoadMore = status
        xAdapter?.refreshLoadController?.setRefreshStatus(status)
    }

    /**
     * 设置是否可以刷新
     */
    fun setRefresh(status: Boolean){
        canRefresh = status
        xAdapter?.refreshLoadController?.setLoadMoreStatus(status)
    }

    /**
     * 设置刷新view
     */
    fun setRefreshView(view: View){
        changeView(view)
        xAdapter?.refreshLoadController?.refreshView = view
    }

    /**
     * 设置加载更多的view
     */
    fun setLoadView(view: View){
        changeView(view)
        xAdapter?.refreshLoadController?.loadView = view
    }


    /**
     * 改变布局样式  主要用于headView 和FootView
     */
    private fun changeView(view:View){
        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        view.layoutParams = params
        view.setPadding(0,dp2px(10F),0,dp2px(10F))
    }

    /**
     * dp转px
     */
    private fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if (canRefresh && canLoadMore){

        }
        return super.onTouchEvent(e)
    }

    override fun onDraw(c: Canvas?) {
        super.onDraw(c)
    }
}