package uz.gita.eventsapp.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.dao.EventsDao
import javax.inject.Inject

@AndroidEntryPoint
class EventReceiver : BroadcastReceiver() {
    private lateinit var mediaPlayer: MediaPlayer
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var eventsDao: EventsDao

    override fun onReceive(context: Context?, intent: Intent?) {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        scope.launch {
            val events = eventsDao.getAllEnableEvents()

            for (i in events.indices) {
                Log.d("TTT", "events receiver get: ${events[i]}")
                when (intent?.action) {
                    events[i].events -> {
                        mediaPlayer.start()
                    }
                }
            }
        }
    }

    fun clearReceiver(){
        scope.cancel()
    }
}