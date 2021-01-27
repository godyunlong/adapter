package com.xiuone.test

import com.xiuone.adapter.adapter.RecyclerSingleAdapter
import com.xiuone.adapter.adapter.RecyclerViewHolder

class TestAdapter : RecyclerSingleAdapter<String>(R.layout.item_test){
    override fun bindView(holder: RecyclerViewHolder<String>, item: String, position: Int) {

    }
}