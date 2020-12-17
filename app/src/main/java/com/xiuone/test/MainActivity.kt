package com.xiuone.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiuone.adapter.view.WaveHeadView

class MainActivity : AppCompatActivity() {
    private var recyclerView:XRecyclerView?=null
    private var adapter:TestAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = TestAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.canRefresh = true
        recyclerView?.canLoadMore = true
        val data = ArrayList<String>()
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        data.add("")
        adapter?.dataController?.setNewData(data)
    }
}