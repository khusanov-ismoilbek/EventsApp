package uz.gita.eventsapp.domain.repository.impl

import android.content.Intent
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.dao.EventsDao
import uz.gita.eventsapp.data.local.entity.EventsEntity
import uz.gita.eventsapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl
@Inject constructor(
    private val eventsDao: EventsDao
) : AppRepository {
    override suspend fun getAllDisableEvents(): List<EventsEntity> {
        if (!eventsDao.isInitialized()) {
            eventsDao.insertInitializedEvents(setInitialized())
        }
        return eventsDao.getAllDisableEvents()
    }

    override suspend fun getAllEnableEvents(): List<EventsEntity> {
        if (!eventsDao.isInitialized()) {
            eventsDao.insertInitializedEvents(setInitialized())
        }
        return eventsDao.getAllEnableEvents()
    }

    override suspend fun updateEventStateToDisable(eventId: Int) {
        eventsDao.updateEventStateToDisable(eventId)
    }

    override suspend fun updateEventStateToEnable(eventId: Int) {
        eventsDao.updateEventStateToEnable(eventId)
    }

    private fun setInitialized(): List<EventsEntity> {
        return listOf(
            EventsEntity(
                id = 1,
                eventIcon = R.drawable.ic_screen_on,
                eventName = R.string.screen_on,
                events = Intent.ACTION_SCREEN_ON
            ),

            EventsEntity(
                id = 2,
                eventIcon = R.drawable.ic_screen_off,
                eventName = R.string.screen_off,
                events = Intent.ACTION_SCREEN_OFF
            ),

            EventsEntity(
                id = 3,
                eventIcon = R.drawable.ic_connected,
                eventName = R.string.battery_charging_on,
                events = Intent.ACTION_POWER_CONNECTED
            ),

            EventsEntity(
                id = 4,
                eventIcon = R.drawable.ic_disconnected,
                eventName = R.string.battery_charging_off,
                events = Intent.ACTION_POWER_DISCONNECTED
            ),

            EventsEntity(
                id = 5,
                eventIcon = R.drawable.ic_storage_low,
                eventName = R.string.storage_low,
                events = Intent.ACTION_DEVICE_STORAGE_LOW
            ),
            EventsEntity(
                id = 6,
                eventIcon = R.drawable.ic_airplane,
                eventName = R.string.text_airplane,
                events = Intent.ACTION_AIRPLANE_MODE_CHANGED
            ),
            EventsEntity(
                id = 7,
                eventIcon = R.drawable.ic_battery_ok,
                eventName = R.string.text_battery_ok,
                events = Intent.ACTION_BATTERY_OKAY
            ),
            EventsEntity(
                id = 8,
                eventIcon = R.drawable.ic_battery_low,
                eventName = R.string.text_battery_low,
                events = Intent.ACTION_BATTERY_LOW
            ),
            EventsEntity(
                id = 8,
                eventIcon = R.drawable.ic_shutdown,
                eventName = R.string.text_shut_down,
                events = Intent.ACTION_SHUTDOWN
            ),
            EventsEntity(
                id = 9,
                eventIcon = R.drawable.time_zone,
                eventName = R.string.text_time_zone_changed,
                events = Intent.ACTION_TIMEZONE_CHANGED
            ),
            EventsEntity(
                id = 10,
                eventIcon = R.drawable.ic_time_changed,
                eventName = R.string.text_time_changed,
                events = Intent.ACTION_TIME_CHANGED
            ),
            EventsEntity(
                id = 11,
                eventIcon = R.drawable.date_changed,
                eventName = R.string.text_date_changed,
                events = Intent.ACTION_DATE_CHANGED
            ),

            )

    }
}