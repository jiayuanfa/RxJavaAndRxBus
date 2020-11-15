package com.example.rxjavademo

import com.example.rxjavademo.rxbus.MessageEvent
import com.example.rxjavademo.rxbus.RxBusKotlin

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
