package com.micudasoftware.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for a task category.
 *
 * @param id The ID of the task category.
 * @param name The name of the task category.
 * @param color The color of the task category.
 */
@Entity(tableName = "task_categories")
internal data class TaskCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val color: Int
)
