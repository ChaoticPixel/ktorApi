package com.example

import com.example.models.Task
import com.example.models.TaskDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.collections.ArrayList


class TaskController {
    fun getAll(): ArrayList<Task> {
        val tasks: ArrayList<Task> = arrayListOf()
        transaction {
            Tasks.selectAll().map {
                tasks.add(
                    Task(
                        id = it[Tasks.id],
                        type = it[Tasks.type],
                        summary = it[Tasks.summary],
                        description = it[Tasks.description],
                        status = it[Tasks.status]
                    )
                )
            }
        }
        return tasks
    }

    fun get(id: UUID): List<Task> {
        val task =  transaction {
            Tasks.select { Tasks.id eq id }.map {
                Task(
                    id = it[Tasks.id],
                    type = it[Tasks.type],
                    summary = it[Tasks.summary],
                    description = it[Tasks.description],
                    status = it[Tasks.status]
                )
            }
        }
        return task
    }

    fun insert(task: TaskDTO) {
        transaction {
            Tasks.insert {
                it[id] = UUID.randomUUID()
                it[type] = task.type
                it[summary] = task.summary
                it[description] = task.description
                it[status] = task.status
            }
        }
    }

    fun update(task: TaskDTO, id: UUID) {
        transaction {
            Tasks.update({ Tasks.id eq id }) {
                it[type] = task.type
                it[summary] = task.summary
                it[description] = task.description
                it[status] = task.status
            }
        }
    }

    fun delete(id: UUID) {
        transaction {
            Tasks.deleteWhere { Tasks.id eq id }
        }
    }
}

