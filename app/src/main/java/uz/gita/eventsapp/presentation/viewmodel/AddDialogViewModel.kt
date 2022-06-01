package uz.gita.eventsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsapp.data.local.model.EventsData

interface AddDialogViewModel {
    val getAllDisableEventsLiveData: LiveData<List<EventsData>>
    val onClickDisableEventsLiveData: LiveData<Int>

    fun onClickDisableEvents(pos: Int)
    fun updateEventStateToEnable(eventId: Int)
}