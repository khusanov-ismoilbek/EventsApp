package uz.gita.eventsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.eventsapp.domain.repository.AppRepository
import uz.gita.eventsapp.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @[Binds Singleton]
    fun bindRepository(impl: AppRepositoryImpl): AppRepository
}