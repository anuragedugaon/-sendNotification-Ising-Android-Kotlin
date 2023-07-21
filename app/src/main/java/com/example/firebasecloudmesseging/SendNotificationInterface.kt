package com.example.firebasecloudmesseging

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface SendNotificationInterface {

    @POST("fcm/send")
    fun send(@HeaderMap header: Map<String,String>, @Body notificationData : NotificationData) : Observable<NotificationModel>

    companion object{
        fun Create():SendNotificationInterface{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(SendNotificationInterface::class.java)

        }
    }
}