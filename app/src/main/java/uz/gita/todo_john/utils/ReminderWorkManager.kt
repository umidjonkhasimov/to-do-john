package uz.gita.todo_john.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ReminderWorkManager(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val title = inputData.getString(DUE_DATE_EXTRA) ?: ""
//        val dueDate = LocalDateTime.parse(dueDateInputExtra)
//
//        val duration = Duration.between(LocalDateTime.now(), dueDate)
//        val delay = duration.toMillis()
//
//        if (delay <= 0) {
//            return Result.success()
//        }
//
//        val notificationWorkRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(ReminderWorkManager::class.java)
//            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//            .build()
//
//        WorkManager.getInstance(applicationContext).enqueue(notificationWorkRequest)
//        var tts: TextToSpeech? = null
//
//        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
//            if (it == TextToSpeech.SUCCESS) {
//                val result = tts?.setLanguage(Locale.ENGLISH)
//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//
//                } else {
//                    tts?.speak("Do work", TextToSpeech.QUEUE_FLUSH, null, "")
//                }
//            }
//        })

//        tts.speak("Do work", TextToSpeech.QUEUE_FLUSH, null, "")
        Log.d("GGG", "doWork")
        Notifications.showNotification(title, applicationContext)

        return Result.success()
    }
}

