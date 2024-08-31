package com.micudasoftware.data.repository

import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import java.time.LocalDate

/**
 * Repository interface for managing tasks and task categories.
 */
interface TasksRepository {

    /**
     * Saves a task to the repository.
     *
     * @param task The task to be saved.
     */
    suspend fun saveTask(task: Task)

    /**
     * Retrieves tasks for a specific day.
     *
     * @param day The day for which to retrieve tasks.
     * @return A list of tasks for the specified day.
     */
    suspend fun getTasksForDay(day: LocalDate): List<Task>

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId The ID of the task to retrieve.
     * @return The task with the specified ID, or null if not found.
     */
    suspend fun getTaskById(taskId: Long): Task?

    /**
     * Deletes a task from the repository.
     *
     * @param task The task to be deleted.
     */
    suspend fun deleteTask(task: Task)

    /**
     * Saves a task category to the repository.
     *
     * @param taskCategory The task category to be saved.
     */
    suspend fun saveTaskCategory(taskCategory: TaskCategory)

    /**
     * Retrieves all task categories.
     *
     * @return A list of all task categories.
     */
    suspend fun getTaskCategories(): List<TaskCategory>

    /**
     * Deletes a task category from the repository.
     *
     * @param taskCategory The task category to be deleted.
     */
    suspend fun deleteTaskCategory(taskCategory: TaskCategory)
}
