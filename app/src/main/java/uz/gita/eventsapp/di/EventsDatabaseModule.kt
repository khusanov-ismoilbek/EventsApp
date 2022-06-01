package uz.gita.eventsapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.eventsapp.data.local.dao.EventsDao
import uz.gita.eventsapp.data.local.database.EventsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventsDatabaseModule {

    @[Provides Singleton]
    fun provideEventDatabase(@ApplicationContext context: Context): EventsDatabase =
        Room
            .databaseBuilder(context, EventsDatabase::class.java, "Events")
            .fallbackToDestructiveMigration()
            .build()

    @[Provides Singleton]
    fun provideEventDao(database: EventsDatabase): EventsDao = database.eventDao()
}