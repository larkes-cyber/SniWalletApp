package com.example.tonwalletapp.di.data.data_source

import android.content.Context
import com.example.tonwalletapp.data.user_data_source.UserDiskDataSource
import com.example.tonwalletapp.data.user_data_source.UserDiskDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiskDataSourceModule {

    @Provides
    fun provideUserDiskDataSource(context: Context):UserDiskDataSource
    = UserDiskDataSourceImpl(context = context)

}