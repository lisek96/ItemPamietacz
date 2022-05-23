package rafalwojcik.prm.androidService

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import rafalwojcik.prm.R
import rafalwojcik.prm.activity.MainActivity
import kotlin.concurrent.thread

class LocationService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerChannel()
        val notification = NotificationCompat.Builder(this, "5")
            .setContentTitle("TEST")
            .setSmallIcon(R.drawable.ic_baseline_add_a_photo_48)
            .setOngoing(true)
            .addAction(0, "run activity",
                PendingIntent.getActivity(
                    this,
                    1,
                    Intent(this, MainActivity::class.java)
                    ,
                    0

                )).build()

        startForeground(1, notification)

        thread {
            while(true){
                println("LOL")
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    fun registerChannel(){
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(
            "5",
            "General",
            importance
        )
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    fun startForeground(){
        startForegroundService(Intent(this, LocationService::class.java))
    }

}