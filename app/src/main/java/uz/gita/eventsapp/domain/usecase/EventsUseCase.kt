package uz.gita.eventsapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.eventsapp.data.local.model.EventsData

interface EventsUseCase {

    fun getAllDisableEvents(): Flow<List<EventsData>>

    fun getAllEnableEvents(): Flow<List<EventsData>>

    fun updateEventStateToDisable(eventId: Int): Flow<List<EventsData>>

    fun updateEventStateToEnable(eventId: Int): Flow<List<EventsData>>
}