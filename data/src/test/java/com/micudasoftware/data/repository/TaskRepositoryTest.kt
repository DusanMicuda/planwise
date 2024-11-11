package com.micudasoftware.data.repository

import com.micudasoftware.data.database.TaskCategoriesDao
import com.micudasoftware.data.database.TaskCategoryEntity
import com.micudasoftware.data.database.TaskEntity
import com.micudasoftware.data.database.TasksDao
import com.micudasoftware.data.repository.model.Task
import com.micudasoftware.data.repository.model.TaskCategory
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneId

class TasksRepositoryTest {

    private lateinit var tasksDao: TasksDao
    private lateinit var taskCategoriesDao: TaskCategoriesDao
    private lateinit var tasksRepository: TasksRepositoryImpl

    private val task = Task(
        id = 1,
        name = "Test Task",
        description = "Test Description",
        startDateTime = OffsetDateTime.parse("2021-01-01T00:00:00+00:00").toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime(),
        endDateTime = OffsetDateTime.parse("2021-01-01T01:00:00+00:00").toInstant().atZone(ZoneId.systemDefault()).toOffsetDateTime(),
        isCompleted = false,
        categoryId = 1,
        reminders = emptyList()
    )

    private val tasks = listOf(task)

    private val taskEntity = TaskEntity(
        id = 1,
        title = "Test Task",
        description = "Test Description",
        categoryId = 1,
        startDate = OffsetDateTime.parse("2021-01-01T00:00:00+00:00").toInstant().toEpochMilli(),
        endDate = OffsetDateTime.parse("2021-01-01T01:00:00+00:00").toInstant().toEpochMilli(),
        reminders = emptyList(),
        isCompleted = false
    )

    private val taskEntities = listOf(taskEntity)

    private val category = TaskCategory(id = 1, name = "Test Category", color = 0x0000FF)

    private val categories = listOf(category)

    private val categoryEntities = listOf(TaskCategoryEntity(1, "Test Category", 0x0000FF))

    @Before
    fun setUp() {
        tasksDao = mockk()
        taskCategoriesDao = mockk()
        tasksRepository = TasksRepositoryImpl(tasksDao, taskCategoriesDao)
    }

    @Test
    fun `Given valid task, When saveTask is called, Then task is saved successfully`() = runBlocking {
        coEvery { tasksDao.insertTask(any()) } returns 123

        tasksRepository.saveTask(task)

        coVerify { tasksDao.insertTask(task.toEntity()) }
    }

    @Test
    fun `Given valid day, When getTasksForDay is called, Then tasks for given day are returned`() = runBlocking {
        val day = OffsetDateTime.now()
        coEvery { tasksDao.getTasksForDay(any()) } returns taskEntities

        val result = tasksRepository.getTasksForDay(day)

        assertEquals(tasks, result)
    }

    @Test
    fun `Given valid taskId, When getTaskById is called, Then task is returned if exists`() = runBlocking {
        coEvery { tasksDao.getTaskById(taskId = 1) } returns taskEntity

        val result = tasksRepository.getTaskById(taskId = 1)

        assertEquals(task, result)
    }

    @Test
    fun `Given valid task, When deleteTask is called, Then task is deleted successfully`() = runBlocking {
        coEvery { tasksDao.deleteTask(any()) } just Runs

        tasksRepository.deleteTask(task)

        coVerify { tasksDao.deleteTask(task.toEntity()) }
    }

    @Test
    fun `Given valid taskId, When deleteTaskById is called, Then task is deleted by id successfully`() = runBlocking {
        coEvery { tasksDao.deleteTaskById(any()) } just Runs

        tasksRepository.deleteTaskById(taskId = 1)

        coVerify { tasksDao.deleteTaskById(taskId = 1) }
    }

    @Test
    fun `Given valid category, When saveTaskCategory is called, Then category is saved successfully`() = runBlocking {
        coEvery { taskCategoriesDao.insertTaskCategory(any()) } just Runs

        tasksRepository.saveTaskCategory(category)

        coVerify { taskCategoriesDao.insertTaskCategory(category.toEntity()) }
    }

    @Test
    fun `Given valid categories, When getTaskCategories is called, Then all categories are returned`() = runBlocking {
        coEvery { taskCategoriesDao.getAllTaskCategories() } returns categoryEntities

        val result = tasksRepository.getTaskCategories()

        assertEquals(categories, result)
    }

    @Test
    fun `Given valid category, When deleteTaskCategory is called, Then category is deleted successfully`() = runBlocking {
        coEvery { taskCategoriesDao.deleteTaskCategory(any()) } just Runs

        tasksRepository.deleteTaskCategory(category)

        coVerify { taskCategoriesDao.deleteTaskCategory(category.toEntity()) }
    }

    @Test
    fun `Given valid categoryId, When deleteTaskCategoryById is called, Then category is deleted by id successfully`() = runBlocking {
        coEvery { taskCategoriesDao.deleteTaskCategoryById(any()) } just Runs

        tasksRepository.deleteTaskCategoryById(taskCategoryId = 1)

        coVerify { taskCategoriesDao.deleteTaskCategoryById(taskCategoryId = 1) }
    }

    @Test
    fun `Given category is used, When isCategoryUsed is called, Then returns true`() = runBlocking {
        coEvery { taskCategoriesDao.isCategoryUsed(any()) } returns true

        val result = tasksRepository.isCategoryUsed(categoryId = 1)

        assertTrue(result)
    }

    @Test
    fun `Given category is not used, When isCategoryUsed is called, Then returns false`() = runBlocking {
        coEvery { taskCategoriesDao.isCategoryUsed(any()) } returns false

        val result = tasksRepository.isCategoryUsed(categoryId = 1)

        assertFalse(result)
    }
}