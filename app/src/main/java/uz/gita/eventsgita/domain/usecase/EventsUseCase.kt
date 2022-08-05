package uz.gita.eventsgita.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.eventsgita.data.local.model.EventsData

interface EventsUseCase {

    fun getAllDisableEvents(): Flow<List<EventsData>>

    fun getAllEnableEvents(): Flow<List<EventsData>>

    fun updateEventStateToDisable(eventId: Int): Flow<Unit>

    fun updateEventStateToEnable(eventId: Int): Flow<Unit>
}