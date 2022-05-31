package rafalwojcik.prm.androidService

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.location.*
import rafalwojcik.prm.R
import rafalwojcik.prm.activity.MainActivity
import rafalwojcik.prm.broadcastreceiver.GeofenceReceiver
import rafalwojcik.prm.dao.ProductDao
import rafalwojcik.prm.database.DatabaseGiver
import java.net.URI.create
import kotlin.concurrent.thread
import kotlin.math.log

class LocationService : Service() {


    @SuppressLint("MissingPermission")
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
            val productDao = DatabaseGiver.getDb(this).productDao()
            val products = productDao.getAll()
            val geofences = mutableListOf<Geofence>()
            products.forEach { product ->
                    var geofence = Geofence.Builder().setCircularRegion(
                        product.latitude, product.longitude, 100f
                    )
                        .setRequestId(product.productName)
                        .setExpirationDuration(3600)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build()
                geofences.add(geofence)
            }

            val geofencesRequest =GeofencingRequest.Builder().apply {
                setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                addGeofences(geofences)
            }.build()

            val intent = Intent(this, GeofenceReceiver::class.java)
            val geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            LocationServices.getGeofencingClient(this)
                .addGeofences(geofencesRequest, geofencePendingIntent).run {
                addOnSuccessListener { println("success geofences") }
                addOnFailureListener { println("failure geofences") }
            }
        }

        thread {
            var locClient = LocationServices.getFusedLocationProviderClient(this)
            var locReq = LocationRequest.create()
                .setExpirationDuration(10000)
                .setInterval(10000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            var locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    println(locationResult?.lastLocation)
                }
            }
            locClient.requestLocationUpdates(locReq, locationCallback, Looper.getMainLooper())
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