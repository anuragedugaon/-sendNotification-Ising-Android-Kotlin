package com.example.firebasecloudmesseging

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
lateinit var title:EditText
lateinit var descreiption:EditText
lateinit var button: Button

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title=findViewById(R.id.title)
        descreiption=findViewById(R.id.description)
        button=findViewById(R.id.sendNotification)

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            Log.d("Anurag", it.toString())
        }

        val tokenList: List<String> =
            mutableListOf("eJu3wg0vRbCBKJfFmTQBxn:APA91bHpjte9EsUsFHqwWI0EgBVXC9ELBBfMi-Ca5YE1VEtPQBHB5QskFeMqjnIeFaoyI2U7z_iFnIRwvzjk7zagrn5CHzY4cwdkzLoqIlIErzRZkaGJH-ESR9vc5PzBOaSliyHAnHLf")
        val headerMap =
            hashMapOf<String, String>("Authorization" to "key=AAAARodbGCM:APA91bFJKpJtsncaPXDT1I9pOgQY99O1W18kI7DYbPbD09VfS_ekGzX8032U0rqwXnhYej3gck89uTMhXXUGziHe1W9nIafH8DS2Ss9vaWuE4kJsqYp-GdMq7vvUL3FIpcT6fz0zQeYJ")

        val notificationData = NotificationData(
            Notification(
                "com.example.firebasecloudmesseging",
                title.text.toString(),
                true,
                descreiption.text.toString()
            ), tokenList
        )

        button.setOnClickListener {
            SendNotificationInterface.Create().send(headerMap, notificationData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                   val notification = it
                }
            sendNotification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification() {

        val chanelId = "com.example.firebasecloudmesseging"
        val channelName = "com.example.firebase"

        val imp = NotificationManager.IMPORTANCE_HIGH

        val notificationChannel = NotificationChannel(chanelId, channelName, imp)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Toast.makeText(this, Build.VERSION_CODES.O.toString(), Toast.LENGTH_SHORT).show()

            val notification = NotificationCompat.Builder(this, chanelId)

                .setAutoCancel(true)

                .setContentTitle(title.text.toString())

                .setContentText(descreiption.text.toString())

                .setSmallIcon(R.drawable.ic_launcher_background)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)

            notificationManager.notify(0, notification.build())

        } else {
            val notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Not")
                .setContentText("This is running")
            val notificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }

    }
}