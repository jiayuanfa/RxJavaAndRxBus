package com.example.recycleviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

abstract class BaseActivity : AppCompatActivity() {

    private var mPresenter: BasePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        initView()
        setContentView(getContentViewId())

        Logger.addLogAdapter(AndroidLogAdapter())
    }

    protected abstract fun getContentViewId(): Int
    protected abstract fun initPresenter()
    protected abstract fun initView()

    fun setPresenter(presenter: BasePresenter) {
        mPresenter = presenter
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unSubscribe()
        Logger.t("fage").d("移除请求")
    }
}