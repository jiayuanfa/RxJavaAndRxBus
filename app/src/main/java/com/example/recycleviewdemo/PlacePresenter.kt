package com.example.recycleviewdemo

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class PlacePresenter: BasePresenter() {

    companion object {
        const val TAG = "IpPresenter"
    }

    fun getPlaceInfoFormation() {

        val baseUrl = "https://api.caiyunapp.com/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val placeService = retrofit.create(PlaceService::class.java)
        val subscription = placeService.searchPlaces("杭州")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<PlaceResponse>() {
                override fun onNext(t: PlaceResponse) {
                    Logger.json(TAG, t)
                }

                override fun onCompleted() {
                    Logger.d(TAG,"onCompleted")
                }

                override fun onError(e: Throwable) {
                    Logger.d(TAG,"onError")
                }
            })
        addSubscription(subscription)
    }
}