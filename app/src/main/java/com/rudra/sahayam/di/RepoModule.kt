package com.rudra.sahayam.di

import com.rudra.sahayam.data.repository.FakeDataRepository
import com.rudra.sahayam.domain.repository.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideDataRepository(): DataRepository = FakeDataRepository()
}
