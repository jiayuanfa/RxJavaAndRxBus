package com.example.rxjavademo

import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class BasePresenter {

    private val compositeSubscription = CompositeSubscription()

    fun addSubscription(sub: Subscription) {
        compositeSubscription.add(sub)
    }

    fun unSubscribe() {
        compositeSubscription.unsubscribe()
    }
}