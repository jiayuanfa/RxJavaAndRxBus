package com.example.recycleviewdemo

import android.util.Log
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers.io
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {

    private val mPresenter: PlacePresenter by lazy {
        PlacePresenter()
    }

    companion object {
        const val TAG = "RxJava"
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initPresenter() {
        setPresenter(mPresenter)
        mPresenter.getPlaceInfoFormation()
    }

    override fun initView() {
        zip()
    }

    /**
     * RxJava Zip
     * 创建多个观察者
     * zip
     * object : Func2<T1, T2, ... , R>
     * @param T1 第一个的类型
     * @param T2 第二个的类型
     * ...       第N个的类型
     * @param R 合并后的结果类型
     */
    private fun zip() {
        val observable1 = Observable.just(1,2,3)
        val observable2 = Observable.just(3,4,5)

       Observable.zip(observable1, observable2) {
           t1, t2 -> t1 + t2
       }.subscribe {
           Log.d(TAG, it.toString())
           Log.d(TAG, Thread.currentThread().name)
       }
    }

    /**
     * 数据去重
     */
    private fun distinct() {
        Observable.just(1,2,2,3,4,5).distinct().subscribe {
            Log.d(TAG, it.toString())
        }
    }

    /**
     * 过滤数据
     */
    private fun filterTest() {

        for (bean in getData()) {
            Observable.just(bean).filter { bean1 ->
                bean1.age > 26 }.subscribe {
                bean2 ->
                Log.d(TAG, "bean=${bean2.age}") }
        }

    }

    fun getData(): List<Bean> {
        val list = ArrayList<Bean>()
        val bean1 = Bean("fage1", 27)
        val bean2 = Bean("fage2", 24)
        val bean3 = Bean("fage", 26)
        val bean4 = Bean("fage", 26)
        val bean5 = Bean("fage5", 20)

        list.add(bean1)
        list.add(bean2)
        list.add(bean3)
        list.add(bean4)
        list.add(bean5)
        return list
    }

    /**
     * 集合转换
     * 给每个元素添加东西
     */
    private fun flatMap() {

        val list = ArrayList<Bean>()
        val bean1 = Bean("fage1", 27)
        val bean2 = Bean("fage2", 27)
        val bean3 = Bean("fage3", 27)
        val bean4 = Bean("fage4", 27)
        val bean5 = Bean("fage5", 27)

        list.add(bean1)
        list.add(bean2)
        list.add(bean3)
        list.add(bean4)
        list.add(bean5)

        Observable.from(list).flatMap { t ->
            Observable.just(Bean(t.name, t.age + 1))
        }.cast(Bean::class.java)
                .subscribe{
                    Log.d(TAG, "Flapmap=${it.name} + ${it.age}")
                    Log.d(TAG, Thread.currentThread().name)
                }
    }

    /**
     * 整数序列范围循环
     * 可用于代替for循环
     */
    private fun intervalRange() {

        Observable.range(0, 60)
                .subscribe {
                    runOnUiThread {
                        Log.d(TAG, "Result$it")
                    }
                }
    }

    /**
     * 间隔回调
     */
    private fun intervalTest() {

        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe {
                    runOnUiThread {
                        Log.d(TAG, "Result$it")
                        Log.d(TAG, Thread.currentThread().name)
                    }
                }
    }

    /**
     * 观察者与被观察者
     * 切换线程
     * 订阅
     * 处理回调
     */
    private fun subscriber() {

        val subscriber = object : Subscriber<String>() {
            override fun onNext(t: String?) {
                Log.d(TAG, "onNext$t")
            }

            override fun onCompleted() {
                Log.d(TAG, "onCompleted")
            }

            override fun onError(e: Throwable?) {
                Log.d(TAG, "onError")
            }

            override fun onStart() {
                Log.d(TAG, "onStart")
            }
        }

        val observable = Observable.create(Observable.OnSubscribe<String> {
            subscriber.onNext("Fage")
            subscriber.onNext("HaiLiting")
            subscriber.onCompleted()
        }).subscribeOn(io())
                .observeOn(AndroidSchedulers.mainThread()).apply {
                    subscribe(subscriber)
                }
    }
}