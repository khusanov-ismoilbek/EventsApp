package uz.gita.eventsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.eventsapp.data.local.model.EventsData

interface EventViewModel {
    val getAllEnableEventsLiveData: LiveData<List<EventsData>>
    val onClickMoreLiveData: LiveData<Unit>
    val onClickShareLiveData: LiveData<Unit>
    val onClickRateLiveData: LiveData<Unit>
    val onClickFeedbackLiveData: LiveData<Unit>

    fun onClickAddDialogButton()
    fun onCLickOkBtnOfAddDialog()
    fun updateEventStateToDisable(eventId: Int)
    fun onClickMore()
    fun onClickShare()
    fun onClickRate()
    fun onCLickFeedback()

    fun setOnClickAddDialogLiveDataListener(block: () -> Unit)
}