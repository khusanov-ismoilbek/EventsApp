package uz.gita.eventsgita.domain.repository

import uz.gita.eventsgita.data.local.entity.EventsEntity

interface AppRepository {
    suspend fun getAllDisableEvents(): List<EventsEntity>

    suspend fun getAllEnableEvents(): List<EventsEntity>

    suspend fun updateEventStateToDisable(eventId: Int)

    suspend fun updateEventStateToEnable(eventId: Int)
}