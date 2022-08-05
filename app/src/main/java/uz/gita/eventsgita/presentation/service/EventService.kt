package uz.gita.eventsgita.presentation.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import uz.gita.eventsgita.MainActivity
import uz.gita.eventsgita.R
import uz.gita.eventsgita.presentation.broadcast.EventReceiver

class EventService : Service() {
    private val CHANNEL_ID = "DEMO"
    private val receiver = EventReceiver()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return if (intent?.extras?.get("STOP") != "STOP") {
            val events = intent?.extras?.getStringArrayList("enabledActions")
            registerReceiver(receiver, IntentFilter().apply {
                for (i in events?.indices!!) {
                    addAction(events[i])
                }
            })
            START_NOT_STICKY
        } else {
            stopSelf()
            START_NOT_STICKY
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        receiver.clearReceiver()
        unregisterReceiver(receiver)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, "EVENT", NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(null, null)
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
        }
    }

    private fun startService() {
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent
            .getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Event Sound")
            .setCustomContentView(createNotificationLayout())
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationLayout(): RemoteViews {
        val view = RemoteViews(packageName, R.layout.remote_view)
        view.setOnClickPendingIntent(R.id.closeButton, createPendingIntent())
        return view
    }

    private fun createPendingIntent(): PendingIntent {
        val intent = Intent(this, EventService::class.java)
        intent.putExtra("STOP", "STOP")
        return PendingIntent
            .getService(
                this,
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
    }
}