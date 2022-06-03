package uz.gita.eventsapp.presentation.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.eventsapp.BuildConfig
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.databinding.ScreenEventsBinding
import uz.gita.eventsapp.presentation.service.EventService
import uz.gita.eventsapp.presentation.ui.adapter.EventsScreenAdapter
import uz.gita.eventsapp.presentation.ui.dialog.AddDialog
import uz.gita.eventsapp.presentation.ui.dialog.BottomSheetDialog
import uz.gita.eventsapp.presentation.ui.dialog.DeleteDialog
import uz.gita.eventsapp.presentation.viewmodel.EventViewModel
import uz.gita.eventsapp.presentation.viewmodel.impl.EventViewModelImpl

@AndroidEntryPoint
class EventsScreen : Fragment(R.layout.screen_events) {
    private val binding by viewBinding(ScreenEventsBinding::bind)
    private val viewModel: EventViewModel by viewModels<EventViewModelImpl>()
    private val adapter = EventsScreenAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel.getAllEnableEventsLiveData.observe(viewLifecycleOwner, getAllEnableEventsObserver)
        viewModel.onClickMoreLiveData.observe(viewLifecycleOwner, onClickMoreObserver)
        viewModel.onClickShareLiveData.observe(viewLifecycleOwner, onClickShareObserver)
        viewModel.onClickRateLiveData.observe(viewLifecycleOwner, onClickRateObserver)
        viewModel.onClickFeedbackLiveData.observe(viewLifecycleOwner, onCLickFeedbackObserver)

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

    private val onClickMoreObserver = Observer<Unit> {
        val bottomSheetDialog = BottomSheetDialog()

        bottomSheetDialog.setOnClickShareListener {
            viewModel.onClickShare()
        }
        bottomSheetDialog.setOnClickRateListener {
            viewModel.onClickRate()
        }
        bottomSheetDialog.setOnClickFeedbackListener {
            viewModel.onCLickFeedback()
        }

        bottomSheetDialog.show(childFragmentManager, "bottomSheetDialog")

    }
    private val onClickShareObserver = Observer<Unit> {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val body =
            "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
        intent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(intent, "share using"))
    }
    private val onClickRateObserver = Observer<Unit> {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${requireActivity().packageName}")
            )
        )
    }
    private val onCLickFeedbackObserver = Observer<Unit> {
        val emailIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "khusanov.ismoilbek@gmail.com"))
        startActivity(emailIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.moreMenu -> {
                viewModel.onClickMore()
                true
            }
            else -> false
        }
    }
}