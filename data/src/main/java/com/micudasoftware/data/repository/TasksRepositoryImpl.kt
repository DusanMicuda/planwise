package com.micudasoftware.data.repository

import com.micudasoftware.data.database.TaskCategoriesDao
import com.micudasoftware.data.database.TasksDao
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime

/**
 * Implementation of the [TasksRepository] interface.
 *
 * @param tasksDao The DAO for tasks.
 * @param taskCategoriesDao The DAO for task categories.
 */
internal class TasksRepositoryImpl(
    private val tasksDao: TasksDao,
    private val taskCategoriesDao: TaskCategoriesDao,
): TasksRepository {

    override suspend fun saveTask(task: Task) {
        withContext(Dispatchers.IO) {
            tasksDao.insertTask(task.toEntity())
        }
    }

    override suspend fun getTasksForDay(day: OffsetDateTime): List<Task> = withContext(Dispatchers.IO) {
        val millisAtStartOfDay = day.toLocalDate().atStartOfDay().toInstant(day.offset).toEpochMilli()
        tasksDao.getTasksForDay(millisAtStartOfDay).map { Task.fromEntity(it) }
    }

    override suspend fun getTaskById(taskId: Long): Task? = withContext(Dispatchers.IO) {
        tasksDao.getTaskById(taskId)?.let { Task.fromEntity(it) }
    }

    override suspend fun deleteTask(task: Task) = withContext(Dispatchers.IO) {
        tasksDao.deleteTask(task.toEntity())
    }

    override suspend fun deleteTaskById(taskId: Long) = withContext(Dispatchers.IO) {
        tasksDao.deleteTaskById(taskId)
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

    override suspend fun deleteTaskCategoryById(taskCategoryId: Long) = withContext(Dispatchers.IO) {
        taskCategoriesDao.deleteTaskCategoryById(taskCategoryId)
    }

    override suspend fun isCategoryUsed(categoryId: Long): Boolean = withContext(Dispatchers.IO) {
        taskCategoriesDao.isCategoryUsed(categoryId)
    }
}