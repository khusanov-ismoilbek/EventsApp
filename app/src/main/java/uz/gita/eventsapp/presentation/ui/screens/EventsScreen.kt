package uz.gita.eventsapp.presentation.ui.screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.databinding.ScreenEventsBinding
import uz.gita.eventsapp.presentation.service.EventService
import uz.gita.eventsapp.presentation.ui.adapter.EventsScreenAdapter
import uz.gita.eventsapp.presentation.ui.dialog.AddDialog
import uz.gita.eventsapp.presentation.ui.dialog.DeleteDialog
import uz.gita.eventsapp.presentation.viewmodel.EventViewModel
import uz.gita.eventsapp.presentation.viewmodel.impl.EventViewModelImpl

@AndroidEntryPoint
class EventsScreen : Fragment(R.layout.screen_events) {
    private val binding by viewBinding(ScreenEventsBinding::bind)
    private val viewModel: EventViewModel by viewModels<EventViewModelImpl>()
    private val adapter = EventsScreenAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllEnableEventsLiveData.observe(viewLifecycleOwner, getAllEnableEventsObserver)
        viewModel.setOnClickAddDialogLiveDataListener {
            val addDialog = AddDialog()

            addDialog.setOnClickOkClickListener {
                viewModel.onCLickOkBtnOfAddDialog()
            }

            addDialog.show(childFragmentManager, "AddDialog")
        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.onClickAddDialogButton()
        }

        adapter.setOnLongClickItemListener { eventId ->
            val deleteDialog = DeleteDialog()

            deleteDialog.setOnClickYesListener {
                viewModel.updateEventStateToDisable(eventId)
            }

            deleteDialog.show(childFragmentManager, "deleteDialog")
        }
    }

    private val getAllEnableEventsObserver = Observer<List<EventsData>> { list ->
        val arrayList = ArrayList<String>()

        for (i in list.indices) {
            arrayList.add(list[i].events)
        }
        val intent = Intent(requireContext(), EventService::class.java)
        intent.putStringArrayListExtra("enabledActions", arrayList)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(intent)
        } else {
            requireActivity().startService(intent)
        }
        adapter.submitList(list)
        binding.recyclerScreen.adapter = adapter
        binding.recyclerScreen.layoutManager = LinearLayoutManager(requireContext())
    }
    private val onClickAddDialogObserver = Observer<Unit> {
        val addDialog = AddDialog()

        addDialog.setOnClickOkClickListener {
            viewModel.onCLickOkBtnOfAddDialog()
        }

        addDialog.show(childFragmentManager, "AddDialog")
    }
}