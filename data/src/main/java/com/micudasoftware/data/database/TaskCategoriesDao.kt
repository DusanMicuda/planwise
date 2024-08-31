package com.micudasoftware.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Data access object for task categories.
 */
@Dao
internal interface TaskCategoriesDao {

    /**
     * Inserts a task category into the task categories table.
     *
     * @param taskCategoryEntity The task category to be inserted.
     */
    @Insert
    fun insertTaskCategory(taskCategoryEntity: TaskCategoryEntity)

    /**
     * Retrieves all task categories.
     *
     * @return A list of all task categories.
     */
    @Query("SELECT * FROM task_categories")
    fun getAllTaskCategories(): List<TaskCategoryEntity>

    /**
     * Retrieves a task category by its ID.
     *
     * @param taskCategoryId The ID of the task category to retrieve.
     * @return The task category with the specified ID, or null if not found.
     */
    @Query("SELECT * FROM task_categories WHERE id = :taskCategoryId")
    fun getTaskCategoryById(taskCategoryId: Long): TaskCategoryEntity?

    /**
     * Deletes a task category from the task categories table.
     *
     * @param taskCategoryEntity The task category to be deleted.
     */
    @Delete
    fun deleteTaskCategory(taskCategoryEntity: TaskCategoryEntity)
}