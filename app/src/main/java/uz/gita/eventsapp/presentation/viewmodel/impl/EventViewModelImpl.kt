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

    override val getAllEnableEventsLiveData = MutableLiveData<List<EventsData>>()
    override val onClickAddDialogLiveData = MutableLiveData<Unit>()

    override fun onClickAddDialogButton() {
        onClickAddDialogLiveData.value = Unit
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

    }
}