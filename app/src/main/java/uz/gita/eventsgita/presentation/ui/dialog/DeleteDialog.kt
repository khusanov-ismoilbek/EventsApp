package uz.gita.eventsgita.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.eventsgita.R
import uz.gita.eventsgita.databinding.DialogDeleteBinding

class DeleteDialog : DialogFragment(R.layout.dialog_delete) {
    private val binding by viewBinding(DialogDeleteBinding::bind)
    private var onCLickYesListener: (() -> Unit)? = null

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
        binding.yes.setOnClickListener {
            onCLickYesListener?.invoke()
            dismiss()
        }

        binding.no.setOnClickListener { dismiss() }
    }

    fun setOnClickYesListener(block: () -> Unit) {
        onCLickYesListener = block
    }

}