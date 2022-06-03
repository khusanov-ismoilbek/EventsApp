package uz.gita.eventsapp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.domain.usecase.EventsUseCase
import uz.gita.eventsapp.presentation.viewmodel.EventViewModel
import javax.inject.Inject

@HiltViewModel
class EventViewModelImpl
@Inject constructor(
    private val useCase: EventsUseCase
) : ViewModel(), EventViewModel {

    init {
        loadEventData()
    }

    private var onClickAddDialogLiveDataListener: (() -> Unit)? = null
    override val getAllEnableEventsLiveData = MutableLiveData<List<EventsData>>()
    override val onClickMoreLiveData = MutableLiveData<Unit>()
    override val onClickShareLiveData = MutableLiveData<Unit>()
    override val onClickRateLiveData = MutableLiveData<Unit>()
    override val onClickFeedbackLiveData = MutableLiveData<Unit>()
    private var count = 0
    private var list: List<EventsData> = ArrayList()

    override fun onClickAddDialogButton() {
        onClickAddDialogLiveDataListener?.invoke()
    }

    override fun onCLickOkBtnOfAddDialog() {
        useCase.getAllEnableEvents().onEach {
            getAllEnableEventsLiveData.value = it
        }.launchIn(viewModelScope)
    }

    override fun updateEventStateToDisable(eventId: Int) {
        useCase.updateEventStateToDisable(eventId).onEach {
            getAllEnableEventsLiveData.value = it
        }.launchIn(viewModelScope)
    }


    private fun loadEventData() {
        useCase.getAllEnableEvents().onEach {
            getAllEnableEventsLiveData.value = it
        }.launchIn(viewModelScope)

        useCase.getAllDisableEvents().onEach {
            count = it.size
            list = it
        }.launchIn(viewModelScope)
    }

    override fun setOnClickAddDialogLiveDataListener(block: () -> Unit) {
        onClickAddDialogLiveDataListener = block
    }

    override fun onClickMore() {
        onClickMoreLiveData.value = Unit
    }

    override fun onClickShare() {
        onClickShareLiveData.value = Unit
    }

    override fun onClickRate() {
        onClickRateLiveData.value = Unit
    }

    override fun onCLickFeedback() {
        onClickFeedbackLiveData.value = Unit
    }
}