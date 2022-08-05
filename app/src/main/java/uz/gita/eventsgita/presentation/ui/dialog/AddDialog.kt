package uz.gita.eventsgita.presentation.ui.dialog

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
import uz.gita.eventsgita.R
import uz.gita.eventsgita.data.local.model.EventsData
import uz.gita.eventsgita.databinding.DialogAddBinding
import uz.gita.eventsgita.presentation.ui.adapter.AddDialogAdapter
import uz.gita.eventsgita.presentation.viewmodel.AddDialogViewModel
import uz.gita.eventsgita.presentation.viewmodel.impl.AddDialogViewModelImpl

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
        viewModel.getAllDisableEventsLiveData.observe(viewLifecycleOwner, getAllDisableEventsObserver)
        viewModel.closeDialogLiveData.observe(viewLifecycleOwner, closeDialogObserver)

        adapter.setOnClickItemListener {
            pos = it
            if (pos != -1) {
                binding.okBtn.isEnabled = true
            }
        }

        binding.okBtn.setOnClickListener { viewModel.updateEventStateToEnable(pos) }
        binding.cancelBtn.setOnClickListener { dismiss() }
    }

    private val getAllDisableEventsObserver = Observer<List<EventsData>> {
        adapter.submitList(it)
        binding.recyclerDialog.adapter = adapter
        binding.recyclerDialog.layoutManager = LinearLayoutManager(requireContext())
    }

    private val closeDialogObserver = Observer<Unit> {
        onCLickOkListener?.invoke()
        dismiss()
    }

    fun setOnClickOkClickListener(block: () -> Unit) {
        onCLickOkListener = block
    }
}