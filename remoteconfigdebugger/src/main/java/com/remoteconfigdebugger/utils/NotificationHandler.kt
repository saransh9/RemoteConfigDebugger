package com.remoteconfigdebugger.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.remoteconfigdebugger.R
import com.remoteconfigdebugger.ui.RemoteConfigActivity

internal class NotificationHandler(private val context: Context) {

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.remote_config_debugger_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = context.getString(
                    R.string.remote_config_debugger_channel_desc
                )
            }

            val notificationManager: NotificationManager? =
                ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java
                )
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(context, RemoteConfigActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, 0)
    }

    fun setUpNotification() {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle(context.getString(R.string.remote_config_debugger_name))
            .setContentText(context.getString(R.string.remote_config_debugger_channel_notification_title))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(createPendingIntent())
           // .setOngoing(true)

        with(NotificationManagerCompat.from(context)) {
            notify(
                NOTIFICATION_ID,
                builder.build()
            )
        }
    }
}