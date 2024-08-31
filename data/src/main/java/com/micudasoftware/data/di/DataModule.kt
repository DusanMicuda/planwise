package com.micudasoftware.data.di

import android.content.Context
import androidx.room.Room
import com.micudasoftware.data.database.TasksDatabase
import com.micudasoftware.data.repository.TasksRepository
import com.micudasoftware.data.repository.TasksRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    internal fun provideTasksDatabase(@ApplicationContext context: Context): TasksDatabase =
        Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "tasks-database"
        ).build()

    @Provides
    internal fun provideTasksRepository(tasksDatabase: TasksDatabase): TasksRepository =
        TasksRepositoryImpl(tasksDatabase)
}