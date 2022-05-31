package rafalwojcik.prm.broadcastreceiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context

import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService


import com.google.android.gms.location.GeofencingEvent
import rafalwojcik.prm.R
import rafalwojcik.prm.androidService.LocationService

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        createNotificationChannel(context!!)
        geofencingEvent.triggeringGeofences.forEach{
            println(it.requestId)
            var notification = NotificationCompat.Builder(context!!, "geofence")
                .setSmallIcon(R.drawable.ic_baseline_location_searching_24)
                .setContentTitle("You are near!!!!")
                .setContentText("""You are close to ${it.requestId}!"""")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            NotificationManagerCompat.from(context).notify(1, notification)
        }
    }

    private fun createNotificationChannel(context: Context) {

            val name = "geofence not"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("geofence", name, importance).apply {
                description = "geofence notifications"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(context, LocationService::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
}