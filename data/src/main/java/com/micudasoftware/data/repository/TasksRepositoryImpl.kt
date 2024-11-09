package com.micudasoftware.data.repository

import com.micudasoftware.data.database.TasksDatabase
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime

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

    override suspend fun saveTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insertTask(task.toEntity())
        }
    }

    override suspend fun getTasksForDay(day: OffsetDateTime): List<Task> = withContext(Dispatchers.IO) {
        val millisAtStartOfDay = day.toLocalDate().atStartOfDay().toInstant(day.offset).toEpochMilli()
        taskDao.getTasksForDay(millisAtStartOfDay).map { Task.fromEntity(it) }
    }

    override suspend fun getTaskById(taskId: Long): Task? = withContext(Dispatchers.IO) {
        taskDao.getTaskById(taskId)?.let { Task.fromEntity(it) }
    }

    override suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun deleteTaskById(taskId: Long) = withContext(Dispatchers.IO) {
        taskDao.deleteTaskById(taskId)
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