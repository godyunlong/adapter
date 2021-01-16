package com.xiuone.adapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Space
import androidx.annotation.IdRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.xiuone.adapter.R
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
        if (viewType<0){
            val position = abs(viewType) -1
            val footPosition = position - dataPosition
            return if (position in 0 until headPosition)
                RecyclerViewHolder(dataController.heads[position])
            else if (position in dataPosition until itemCount && footPosition in 0 until dataController.getFootSize())
                RecyclerViewHolder(dataController.foots[footPosition])
            else{
                RecyclerViewHolder(dataController.getEntryView()?:LayoutInflater.from(parent.context).inflate(R.layout.item_space,null))
            }
        }
        return onDataCreateViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int = dataController.getItemCount()

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val headSize = dataController.getHeadSize()
        val headDataSize = headSize+dataController.datas.size
        if (position in headSize until headDataSize){
            val dataPosition = position - headSize
            val item = dataController.datas[position - headSize]
            bindView(holder,item, dataPosition)
            //单点
            if (itemClickListener != null)
                holder.itemView.setOnClickListener {itemClickListener?.onItemClick(this,it,dataPosition)}
            if (itemChildClickListener != null)
                for (item in itemClickChild)
                    holder.getView<View>(item)?.setOnClickListener {
                        itemChildClickListener?.onItemChildClick(this,it,dataPosition)
                    }

            //长按
            if (itemChildLongListener != null) {
                holder.itemView.setOnLongClickListener {
                    itemChildLongListener?.onItemChildLongClick(this, it, dataPosition)
                    false
                }
            }
            if (itemChildLongListener != null)
                for (item in itemLongClickChild)
                    holder.getView<View>(item)?.setOnLongClickListener {
                        itemChildLongListener?.onItemChildLongClick(this, it, dataPosition)
                        false
                    }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val headSize = dataController.getHeadSize()
        val dataEntrySize = dataController.getDataSizeNotEntryView()
        val dataSize = dataController.getDataSize()
        val footSize = dataController.getFootSize()
        if (position in  0 until headSize)
            return -position-1
        else if (position in headSize+dataSize until itemCount)
            return -position-1
        else if (position in headSize until headSize+dataEntrySize)
            return dataType(headSize,position)
        else return -(headSize+dataSize+footSize+10)*2
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

    fun getDataViewHolder(recyclerView: RecyclerView,position: Int):RecyclerViewHolder?{
        if (position in 0 until dataController.getDataSizeNotEntryView()) {
            return getViewHolder(recyclerView, position+dataController.getHeadSize())
        }
        return null
    }

    fun getHeadViewHolder(recyclerView: RecyclerView,position: Int):RecyclerViewHolder?{
        if (position in 0 until dataController.getHeadSize()) {
            return getViewHolder(recyclerView, position)
        }
        return null
    }

    fun getFootViewHolder(recyclerView: RecyclerView,position: Int):RecyclerViewHolder?{
        if (position in 0 until dataController.getFootSize()) {
            return getViewHolder(recyclerView, position+dataController.getHeadSize()+dataController.getDataSize())
        }
        return null
    }


    private fun getViewHolder(recyclerView: RecyclerView,position: Int):RecyclerViewHolder?{
        if (position in 0 until itemCount) {
            val holder = recyclerView.findViewHolderForAdapterPosition(position)
            if (holder is RecyclerViewHolder)
                return holder
        }
        return null
    }



    /**
     * 扩展方法根据需求定
     */
    open fun onAttachedToRecyclerView(position: Int):Int = 1
    abstract fun dataType(headPosition:Int,position: Int):Int
    abstract fun onDataCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerViewHolder
    abstract fun bindView(holder: RecyclerViewHolder, item:T, position: Int)


}