package com.example.recycleviewdemo.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    /**
     * 防止被多个线程同时使用 线程同步
     */
    private static volatile RxBus rxBus;
    private final Subject<Object, Object> subject;

    /**
     * RxBus是非线程安全的 这里采用了SerializedSubject保证线程安全
     */
    private RxBus() {
        subject = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 双锁单例 保证只被创建一次
     * @return
     */
    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    /**
     * 发送事件
     *
     * @param object
     */
    public void post(Object object) {
        subject.onNext(object);
    }

    /**
     * 根据类型接收相应类型事件
     * 只会发送指定类型的数据
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable toObservable(Class<T> eventType) {
        return subject.ofType(eventType);
    }
}
