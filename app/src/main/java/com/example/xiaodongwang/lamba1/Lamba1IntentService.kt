package com.example.xiaodongwang.lamba1

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder


/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
class Lamba1IntentService : IntentService("Lamba1IntentService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.i(LOG_TAG, "receive message")
        val builder = NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Service Lamba1 in processing...")
                .setContentText("Hello World!")
        val resultIntent = Intent(this, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(resultPendingIntent)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, builder.build())
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startAction(context: Context) {
            val intent = Intent(context, Lamba1IntentService::class.java)
            context.startService(intent)
        }

        private val LOG_TAG = "Lamba1IntentService"
    }
}
