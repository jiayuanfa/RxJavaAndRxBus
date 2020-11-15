package com.example.rxjavademo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BaseActivity : AppCompatActivity() {

    private var mPresenter: BasePresenter? = null
    private var mRxCompositeSubscription: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRxCompositeSubscription = CompositeSubscription()

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

    fun addSubscription(sub: Subscription) {
        mRxCompositeSubscription!!.add(sub)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.unSubscribe()
        mRxCompositeSubscription?.unsubscribe()
        Logger.t("fage").d("移除请求")
    }
}