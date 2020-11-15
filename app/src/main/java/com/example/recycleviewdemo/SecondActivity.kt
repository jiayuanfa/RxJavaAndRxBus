package com.example.recycleviewdemo

import com.example.recycleviewdemo.rxbus.MessageEvent
import com.example.recycleviewdemo.rxbus.RxBusKotlin

class SecondActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_second
    }

    override fun initPresenter() {
    }

    override fun initView() {
        // 发出信号
        RxBusKotlin.post(MessageEvent("发哥变了哦"))
    }
}
