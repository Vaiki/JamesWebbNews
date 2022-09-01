package com.vaiki.jameswebbnews.workmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.vaiki.jameswebbnews.R
import com.vaiki.jameswebbnews.api.NewsApi
import com.vaiki.jameswebbnews.ui.NewsActivity
import com.vaiki.jameswebbnews.ui.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

class BreakingNewsWorker(
    private val ctx: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(ctx, workerParams) {
    private var oldNews: String = ""
    private var newNews: String = ""
    companion object {
        const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 1
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {

        val response = NewsApi.api.getBreakingNews()
        return if (response.isSuccessful) {
            newNews = response.body()?.articles?.last()?.url.toString()
            if (oldNews !=newNews){
            val notificationText = response.body()?.articles?.last()?.title
            notificationText?.let { showNotification(it) }
            oldNews = newNews
            Log.e("doWork", "Success")}
            Result.success()
        } else Result.failure()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(desTxt: String) {
        val intent = Intent(applicationContext, NewsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_breaking_news)
            .setContentTitle("Breaking News!")
            .setContentText(desTxt)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "channel name"
            val channelDescription = "channel Description"
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(CHANNEL_ID, channelName, channelImportance).apply {
                description = channelDescription
            }

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(NOTIFICATION_ID, notification.build())
        }


    }
}