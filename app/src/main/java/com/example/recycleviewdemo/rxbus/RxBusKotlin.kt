package com.example.recycleviewdemo.rxbus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

/**
 * RxBus Kotlin版本
 */
object RxBusKotlin {
    private var subject: Subject<Any, Any>? = null

    /**
     * RxBus是非线程安全的 这里采用了SerializedSubject保证线程安全
     */
    init {
        subject = SerializedSubject(PublishSubject.create())
    }

    /**
     * 发送事件
     *
     * @param object
     */
    fun post(obj: Any) {
        subject!!.onNext(obj)
    }

    /**
     * 根据类型接收相应类型事件
     * 只会发送指定类型的数据
     * @param eventType
     * @param <T>
     * @return
    </T> */
    fun <T> toObservable(eventType: Class<T>): Observable<*> {
        return subject!!.ofType(eventType)
    }
}