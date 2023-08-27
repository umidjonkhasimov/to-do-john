package uz.gita.todo_john.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import uz.gita.todo_john.R
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class Notifications {
    companion object {
        fun showNotification(message: String, context: Context) {
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(LongArray(0))

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
                return

            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }

        fun createChannel(context: Context) {
            val name = NOTIFICATION_CHANNEL_NAME
            val description = NOTIFICATION_CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

            notificationManager?.createNotificationChannel(channel)
        }
    }


}
//fun scheduleNotification(date: String, time: String, context: Context) {
//    val workManager = WorkManager.getInstance(context)
//    val dateAndTime = "$date $time"
//    Log.d("GGG", "scheduleNotification: $dateAndTime")
//
//    val simpleDateFormat = SimpleDateFormat("mm/dd/yyyy hh:mm", Locale.getDefault())
//    val scheduledDate = simpleDateFormat.parse(dateAndTime)
//    val timeDiff = scheduledDate!!.time - System.currentTimeMillis()
//    Log.d("GGG", "scheduledDate: ${scheduledDate.time}")
//    Log.d("GGG", "System.currentTimeMillis(): ${System.currentTimeMillis()}")
//    Log.d("GGG", "timeDiff: $timeDiff")
//
//    val work = OneTimeWorkRequestBuilder<ReminderWorkManager>()
//        .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
////        .setInputData(workDataOf(DATE to date, TIME to time))
//        .build()
//
//    workManager.enqueueUniqueWork("NotificationWork", ExistingWorkPolicy.REPLACE, work)
//
//}
