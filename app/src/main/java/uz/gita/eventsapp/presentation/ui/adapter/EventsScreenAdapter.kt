package uz.gita.eventsapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.eventsapp.R
import uz.gita.eventsapp.data.local.model.EventsData
import uz.gita.eventsapp.databinding.ItemOfScreenBinding

class EventsScreenAdapter :
    ListAdapter<EventsData, EventsScreenAdapter.EventViewHolder>(EventsDiffUtil) {
    private var onLongClickItemListener: ((id: Int) -> Unit)? = null

    private object EventsDiffUtil : DiffUtil.ItemCallback<EventsData>() {
        override fun areItemsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: EventsData, newItem: EventsData): Boolean =
            oldItem == newItem

    }

    inner class EventViewHolder(private val binding: ItemOfScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.container.setOnLongClickListener {
                val data = getItem(absoluteAdapterPosition)
                val state = data.eventState
                onLongClickItemListener?.invoke(data.id)
                return@setOnLongClickListener true
            }
        }

        fun bind() {
            binding.eventText.text =
                itemView.resources.getString(getItem(absoluteAdapterPosition).eventName)
            binding.eventImage.setImageResource(getItem(absoluteAdapterPosition).eventIcon)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemOfScreenBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_of_screen, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind()
    }

    fun setOnLongClickItemListener(block: (id: Int) -> Unit) {
        onLongClickItemListener = block
    }
}