package com.micudasoftware.planwise.data.repository

import com.micudasoftware.planwise.data.database.TasksDatabase
import com.micudasoftware.planwise.data.repository.model.Task
import com.micudasoftware.planwise.data.repository.model.TaskCategory
import java.time.LocalDate

/**
 * Implementation of the [TasksRepository] interface.
 *
 * @param tasksDatabase The Room database for storing tasks.
 */
internal class TasksRepositoryImpl(
    tasksDatabase: TasksDatabase
): TasksRepository {

    private val taskDao = tasksDatabase.tasksDao()
    private val taskCategoriesDao = tasksDatabase.taskCategoriesDao()

    override suspend fun saveTask(task: Task) =
        taskDao.insertTask(task.toEntity())

    override suspend fun getTasksForDay(day: LocalDate): List<Task> =
        taskDao.getTasksForDay(day.toEpochDay()).map { Task.fromEntity(it) }

    override suspend fun getTaskById(taskId: Long): Task? =
        taskDao.getTaskById(taskId)?.let { Task.fromEntity(it) }

    override suspend fun deleteTask(task: Task) =
        taskDao.deleteTask(task.toEntity())

    override suspend fun saveTaskCategory(taskCategory: TaskCategory) =
        taskCategoriesDao.insertTaskCategory(taskCategory.toEntity())

    override suspend fun getTaskCategories(): List<TaskCategory> =
        taskCategoriesDao.getAllTaskCategories().map { TaskCategory.fromEntity(it) }

    override suspend fun deleteTaskCategory(taskCategory: TaskCategory) =
        taskCategoriesDao.deleteTaskCategory(taskCategory.toEntity())
}