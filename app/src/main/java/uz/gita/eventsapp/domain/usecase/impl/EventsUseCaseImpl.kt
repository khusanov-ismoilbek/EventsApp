package uz.gita.eventsapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.domain.repository.AppRepository
import uz.gita.eventsapp.domain.usecase.EventsUseCase
import javax.inject.Inject

class EventsUseCaseImpl
@Inject constructor(
    private val repository: AppRepository
) : EventsUseCase {
    private var list: List<EventsData> = ArrayList()

    override fun getAllDisableEvents() = flow<List<EventsData>> {
        val result = repository.getAllDisableEvents().map {
            it.eventsEntityToEventsData()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getAllEnableEvents() = flow<List<EventsData>> {
        val result = repository.getAllEnableEvents().map {
            it.eventsEntityToEventsData()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun updateEventStateToDisable(eventId: Int) = flow<List<EventsData>> {
        repository.updateEventStateToDisable(eventId)
        val result = repository.getAllEnableEvents().map {
            it.eventsEntityToEventsData()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun updateEventStateToEnable(eventId: Int) = flow<List<EventsData>> {
        repository.updateEventStateToEnable(eventId)
        val result = repository.getAllDisableEvents().map {
            it.eventsEntityToEventsData()
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

}