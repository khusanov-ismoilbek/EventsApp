package uz.gita.eventsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsapp.data.local.model.EventsData

interface AddDialogViewModel {
    val getAllDisableEventsLiveData: LiveData<List<EventsData>>
    val closeDialogLiveData : LiveData<Unit>

    fun updateEventStateToEnable(eventId: Int)
}