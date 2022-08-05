package uz.gita.eventsgita.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.eventsgita.R
import uz.gita.eventsgita.data.local.model.EventsData
import uz.gita.eventsgita.databinding.ItemOfDialogBinding

class AddDialogAdapter :
    ListAdapter<EventsData, AddDialogAdapter.AddDialogViewHolder>(AddDialogDiffUtil) {
    private var onClickItemListener: ((Int) -> Unit)? = null
    private var selected = -1

    private object AddDialogDiffUtil : DiffUtil.ItemCallback<EventsData>() {
        override fun areItemsTheSame(oldItem: EventsData, newItem: EventsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EventsData, newItem: EventsData): Boolean {
            return oldItem == newItem
        }
    }

    inner class AddDialogViewHolder(private val binding: ItemOfDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.container.setOnClickListener {
                if (selected > -1 && selected != absoluteAdapterPosition) {
                    getItem(selected).eventState = 0
                    notifyItemChanged(selected)
                }

                selected = absoluteAdapterPosition
                binding.eventDialogRadioButton.isChecked = true
                onClickItemListener?.invoke(getItem(absoluteAdapterPosition).id)
            }
            binding.eventDialogRadioButton.setOnClickListener {
                if (selected > -1 && selected != absoluteAdapterPosition) {
                    getItem(selected).eventState = 0
                    notifyItemChanged(selected)
                }

                selected = absoluteAdapterPosition
                binding.eventDialogRadioButton.isChecked = true
                onClickItemListener?.invoke(getItem(absoluteAdapterPosition).id)
            }
        }

        fun bind() {
            binding.eventDialogText.text =
                itemView.resources.getString(getItem(absoluteAdapterPosition).eventName)
            binding.eventDialogRadioButton.isChecked =
                getItem(absoluteAdapterPosition).eventState == 1

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddDialogViewHolder {
        return AddDialogViewHolder(
            ItemOfDialogBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_of_dialog, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: AddDialogViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnClickItemListener(block: (Int) -> Unit) {
        onClickItemListener = block
    }
}