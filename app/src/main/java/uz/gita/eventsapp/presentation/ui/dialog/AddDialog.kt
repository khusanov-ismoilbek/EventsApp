package uz.gita.eventsapp.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.databinding.DialogAddBinding
import uz.gita.eventsapp.presentation.ui.adapter.AddDialogAdapter
import uz.gita.eventsapp.presentation.viewmodel.AddDialogViewModel
import uz.gita.eventsapp.presentation.viewmodel.impl.AddDialogViewModelImpl

@AndroidEntryPoint
class AddDialog : DialogFragment(R.layout.dialog_add) {
    private val binding by viewBinding(DialogAddBinding::bind)
    private val viewModel: AddDialogViewModel by viewModels<AddDialogViewModelImpl>()
    private val adapter = AddDialogAdapter()
    private var pos = -1

    private var onCLickOkListener: (() -> Unit)? = null

    /**
    background transparent
     * */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllDisableEventsLiveData.observe(
            viewLifecycleOwner,
            getAllDisableEventsObserver
        )
        viewModel.onClickDisableEventsLiveData.observe(
            viewLifecycleOwner,
            onCLickDisableEventsObserver
        )

        adapter.setOnClickItemListener {
            pos = it
            if (pos != -1) {
                binding.okBtn.isEnabled = true
            }
        }

        binding.okBtn.setOnClickListener {
            viewModel.onClickDisableEvents(pos)
            onCLickOkListener?.invoke()
            dismiss()
        }
        binding.cancelBtn.setOnClickListener { dismiss() }
    }

    private val getAllDisableEventsObserver = Observer<List<EventsData>> {
        adapter.submitList(it)
        binding.recyclerDialog.adapter = adapter
        binding.recyclerDialog.layoutManager = LinearLayoutManager(requireContext())
    }
    private val onCLickDisableEventsObserver = Observer<Int> {
        viewModel.updateEventStateToEnable(it)
    }

    fun setOnClickOkClickListener(block: () -> Unit) {
        onCLickOkListener = block
    }
}