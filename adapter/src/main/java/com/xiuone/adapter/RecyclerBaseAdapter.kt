package com.xiuone.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiuone.adapter.controller.RecyclerDataController
import com.xiuone.adapter.controller.RefreshLoadController
import kotlin.math.abs

abstract class RecyclerBaseAdapter<T> :RecyclerView.Adapter<RecyclerViewViewHolder>(){
    val recyclerDataController:RecyclerDataController<T> = RecyclerDataController<T>()
    val refreshLoadController:RefreshLoadController = RefreshLoadController()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val refreshCount = refreshLoadController.refreshCount()
        val headPosition = refreshCount+recyclerDataController.getHeadSize()
        val dataPosition = headPosition+recyclerDataController.getDataSize()
        val footPosition = dataPosition+recyclerDataController.getFootSize()
        if (viewType<=0){
            if (abs(viewType) in 0 until refreshCount)
                return RecyclerViewViewHolder(refreshLoadController.refreshView!!)
            if (abs(viewType) in refreshCount until headPosition)
                return RecyclerViewViewHolder(recyclerDataController.heads[viewType-refreshCount])
            if (abs(viewType) in dataPosition until footPosition)
                return RecyclerViewViewHolder(recyclerDataController.foots[viewType-dataPosition])
            if ((abs(viewType) in headPosition until dataPosition) && recyclerDataController.datas.size<=0)
                return RecyclerViewViewHolder(recyclerDataController.entryView!!)
            if (abs(viewType) >= footPosition)
                RecyclerViewViewHolder(refreshLoadController.loadView!!)
        }
        return onDataCreateViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int = recyclerDataController.getItemCount()+recyclerDataController.getDataSize()

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        val refreshCount = refreshLoadController.refreshCount()
        val headPosition = refreshCount+recyclerDataController.getHeadSize()
        val dataPosition = headPosition+recyclerDataController.datas.size
        if (position in headPosition until dataPosition){
            val item = recyclerDataController.datas[position - headPosition]
            bindView(holder,item, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val refreshCount = refreshLoadController.refreshCount()
        val headPosition = refreshCount+recyclerDataController.getHeadSize()
        val dataPosition = headPosition+recyclerDataController.datas.size
        if (recyclerDataController.datas.size<=0)return position
        if (position < headPosition || position >= dataPosition)
            return -position
        return dataType(headPosition,position)
    }

    /**
     * headView refreshView  loadView entryView 都占满屏
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager?:return
        if (manager is GridLayoutManager){
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val count: Int = manager.spanCount
                    val refreshCount = refreshLoadController.refreshCount()
                    val headPosition = refreshCount+recyclerDataController.getHeadSize()
                    val dataPosition = headPosition+recyclerDataController.datas.size
                    return if (position in  headPosition until  dataPosition)
                        onAttachedToRecyclerView(position)
                    else count
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        val refreshCount = refreshLoadController.refreshCount()
        val headPosition = refreshCount+recyclerDataController.getHeadSize()
        val dataPosition = headPosition+recyclerDataController.datas.size
        val lp = holder.itemView.layoutParams
        if ((position < headPosition || position >= dataPosition )&& lp is StaggeredGridLayoutManager.LayoutParams)
            lp.isFullSpan = true
    }
    /**
     * 扩展方法根据需求定
     */
    open fun onAttachedToRecyclerView(position: Int):Int = 1
    abstract fun dataType(headPosition:Int,position: Int):Int
    abstract fun onDataCreateViewHolder(parent: ViewGroup,viewType: Int):RecyclerViewViewHolder
    abstract fun bindView(holder: RecyclerViewViewHolder,item:T,position: Int)
}