package com.micudasoftware.planwise.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the tasks table.
 */
@Dao
internal interface TasksDao {

    /**
     * Inserts a task into the tasks table.
     * If the task already exists, it replaces it.
     *
     * @param task The task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    /**
     * Retrieves tasks for a specific day.
     *
     * @param day The start of the day in milliseconds since epoch.
     * @return A list of tasks for the specified day.
     */
    @Query("SELECT * FROM tasks WHERE startDate >= :day AND startDate <= :day + 86400000")
    suspend fun getTasksForDay(day: Long): List<TaskEntity>

    /**
     * Retrieves tasks for a specific category.
     *
     * @param categoryId The ID of the category.
     * @return A list of tasks for the specified category.
     */
    @Query("SELECT * FROM tasks WHERE categoryId = :categoryId")
    suspend fun getTasksForCategory(categoryId: Long): List<TaskEntity>

    /**
     * Deletes a task from the tasks table.
     *
     * @param task The task to be deleted.
     */
    @Delete
    suspend fun deleteTask(task: TaskEntity)
}