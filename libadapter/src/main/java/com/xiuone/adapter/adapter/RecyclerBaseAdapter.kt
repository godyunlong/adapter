package com.xiuone.adapter.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiuone.adapter.controller.RecyclerDataController
import com.xiuone.adapter.listener.OnChildItemClickListener
import com.xiuone.adapter.listener.OnChildItemLongClickListener
import com.xiuone.adapter.listener.OnItemClickListener
import com.xiuone.adapter.listener.OnItemLongClickListener
import kotlin.math.abs

abstract class RecyclerBaseAdapter<T> :RecyclerView.Adapter<RecyclerViewHolder>(){
    val dataController:RecyclerDataController<T> = RecyclerDataController<T>(this)
    var itemClickListener:OnItemClickListener?=null
    var itemLongListener:OnItemLongClickListener?=null
    private var itemChildClickListener:OnChildItemClickListener?=null
    private var itemChildLongListener:OnChildItemLongClickListener?=null
    private val itemClickChild = ArrayList<@IdRes Int>()
    private var itemLongClickChild = ArrayList<@IdRes Int>()

    /**
     * 绑定单机
     */
    fun bindItemChildClickListener(itemChildClickListener:OnChildItemClickListener,@IdRes vararg viewIds: Int){
        this.itemChildClickListener = itemChildClickListener
        this.itemClickChild.clear()
        for (item in viewIds)
            this.itemClickChild.add(item)
    }

    /**
     * 绑定item的长按事件
     */
    fun bindItemChildLongClickListener(itemChildLongListener: OnChildItemLongClickListener,@IdRes vararg viewIds: Int){
        this.itemChildLongListener = itemChildLongListener
        this.itemLongClickChild.clear()
        for (item in viewIds)
            this.itemLongClickChild.add(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val headPosition = dataController.getHeadSize()
        val dataPosition = headPosition+dataController.getDataSize()
        val footPosition = dataPosition+dataController.getFootSize()
        if (viewType<=0){
            if (abs(viewType) in 0 until headPosition)
                return RecyclerViewHolder(
                    dataController.heads[abs(viewType)]
                )
            if (abs(viewType) in dataPosition until footPosition)
                return RecyclerViewHolder(
                    dataController.foots[abs(
                        viewType
                    ) - dataPosition]
                )
            if ((abs(viewType) in headPosition until dataPosition) && dataController.datas.size<=0)
                return RecyclerViewHolder(
                    dataController.entryView!!
                )
        }
        return onDataCreateViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int = dataController.getItemCount()

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val headPosition = dataController.getHeadSize()
        val dataPosition = headPosition+dataController.datas.size
        if (position in headPosition until dataPosition){
            val item = dataController.datas[position - headPosition]
            bindView(holder,item, position)
            //单点
            if (itemClickListener != null)
                holder.itemView.setOnClickListener {itemClickListener?.onItemClick(this,it,position)}
            if (itemChildClickListener != null)
                for (item in itemClickChild)
                    holder.getView<View>(item)?.setOnClickListener { itemChildClickListener?.onItemChildClick(this,it,position) }

            //长按
            if (itemChildLongListener != null) {
                holder.itemView.setOnLongClickListener {
                    itemChildLongListener?.onItemChildLongClick(this, it, position)
                    false
                }
            }
            if (itemChildLongListener != null)
                for (item in itemLongClickChild)
                    holder.getView<View>(item)?.setOnLongClickListener {
                        itemChildLongListener?.onItemChildLongClick(this, it, position)
                        false
                    }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val headPosition = dataController.getHeadSize()
        val dataPosition = headPosition+dataController.datas.size
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
                    val headPosition = dataController.getHeadSize()
                    val dataPosition = headPosition+dataController.datas.size
                    return if (position in  headPosition until  dataPosition)
                        onAttachedToRecyclerView(position)
                    else count
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        val headPosition = dataController.getHeadSize()
        val dataPosition = headPosition+dataController.datas.size
        val lp = holder.itemView.layoutParams

        if ((position < headPosition || position >= dataPosition )&& lp is StaggeredGridLayoutManager.LayoutParams)
            lp.isFullSpan = true
    }



    /**
     * 扩展方法根据需求定
     */
    open fun onAttachedToRecyclerView(position: Int):Int = 1
    abstract fun dataType(headPosition:Int,position: Int):Int
    abstract fun onDataCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerViewHolder
    abstract fun bindView(holder: RecyclerViewHolder, item:T, position: Int)


}