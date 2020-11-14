package com.example.recycleviewdemo

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface PlaceService {

    @GET("v2/place?token=aioadiusKswFsuuj&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Observable<PlaceResponse>
}