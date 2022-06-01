package uz.gita.eventsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsapp.data.local.model.EventsData

interface EventViewModel {
    val getAllEnableEventsLiveData: LiveData<List<EventsData>>
    val onClickAddDialogLiveData: LiveData<Unit>

    fun onClickAddDialogButton()
    fun onCLickOkBtnOfAddDialog()
    fun updateEventStateToDisable(eventId: Int)
}