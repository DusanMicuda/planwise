package com.micudasoftware.data.repository

import com.micudasoftware.data.database.TasksDatabase
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun saveTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun getTasksForDay(day: LocalDate): List<Task> = withContext(Dispatchers.IO) {
        taskDao.getTasksForDay(day.toEpochDay()).map { Task.fromEntity(it) }
    }

    override suspend fun getTaskById(taskId: Long): Task? = withContext(Dispatchers.IO) {
        taskDao.getTaskById(taskId)?.let { Task.fromEntity(it) }
    }

    override suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun saveTaskCategory(taskCategory: TaskCategory) = withContext(Dispatchers.IO) {
        taskCategoriesDao.insertTaskCategory(taskCategory.toEntity())
    }

    override suspend fun getTaskCategories(): List<TaskCategory> = withContext(Dispatchers.IO) {
        taskCategoriesDao.getAllTaskCategories().map { TaskCategory.fromEntity(it) }
    }

    override suspend fun deleteTaskCategory(taskCategory: TaskCategory) = withContext(Dispatchers.IO) {
        taskCategoriesDao.deleteTaskCategory(taskCategory.toEntity())
    }
}