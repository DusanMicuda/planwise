package com.micudasoftware.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database for storing tasks.
 */
@Database(entities = [TaskEntity::class, TaskCategoryEntity::class], version = 1)
@TypeConverters(RemindersTypeConverter::class)
internal abstract class TasksDatabase: RoomDatabase() {

    /**
     * Gets the tasks DAO.
     *
     * @return The tasks DAO.
     */
    abstract fun tasksDao(): TasksDao

    /**
     * Gets the task categories DAO.
     *
     * @return The task categories DAO.
     */
    abstract fun taskCategoriesDao(): TaskCategoriesDao
}