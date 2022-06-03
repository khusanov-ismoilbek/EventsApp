package uz.gita.eventsapp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.domain.usecase.EventsUseCase
import uz.gita.eventsapp.presentation.viewmodel.AddDialogViewModel
import javax.inject.Inject

@HiltViewModel
class AddDialogViewModelImpl
@Inject constructor(
    private val useCase: EventsUseCase
) : ViewModel(),
    AddDialogViewModel {

    init {
        loadData()
    }

    override val getAllDisableEventsLiveData = MutableLiveData<List<EventsData>>()
    override val onClickDisableEventsLiveData = MutableLiveData<Int>()

    override fun onClickDisableEvents(pos: Int) {
        onClickDisableEventsLiveData.value = pos
    }

    override fun updateEventStateToEnable(eventId: Int) {
        useCase.updateEventStateToEnable(eventId).onEach {
            getAllDisableEventsLiveData.value = it
        }.launchIn(viewModelScope)
    }

    private fun loadData() {
        useCase.getAllDisableEvents().onEach {
            getAllDisableEventsLiveData.value = it
        }.launchIn(viewModelScope)
    }
}