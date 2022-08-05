package uz.gita.eventsgita.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.eventsgita.data.local.model.EventsData

@Entity(tableName = "events")
data class EventsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventIcon: Int,
    val events: String,
    val eventName: Int,
    var eventState: Int = 0
) {
    fun eventsEntityToEventsData(): EventsData = EventsData(
        id = id,
        eventIcon = eventIcon,
        events = events,
        eventName = eventName,
        eventState = eventState
    )

}