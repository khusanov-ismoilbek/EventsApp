package uz.gita.eventsgita.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsgita.data.local.model.EventsData

interface AddDialogViewModel {
    val getAllDisableEventsLiveData: LiveData<List<EventsData>>
    val closeDialogLiveData : LiveData<Unit>

    fun updateEventStateToEnable(eventId: Int)
}