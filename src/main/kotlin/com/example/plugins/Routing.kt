package com.example.plugins

import com.example.TaskController
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import com.example.models.TaskDTO
import java.util.*

val taskController = TaskController()


fun Route.taskRouting() {
    route("/v1/api/tasks") {
        get {
            call.respond(taskController.getAll())

        }

        post {
            val taskDto = call.receive<TaskDTO>()
            call.respond(taskDto)
            call.respond(taskController.insert(taskDto))

        }
        put("/{id}") {
            val id: UUID = UUID.fromString(call.parameters["id"])
            val taskDTO = call.receive<TaskDTO>()
            taskController.update(taskDTO, id)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id: UUID = UUID.fromString(call.parameters["id"])
            taskController.delete(id)
            call.respond(HttpStatusCode.OK)
        }

        get("/{id}") {
            val id: UUID = UUID.fromString(call.parameters["id"])
            val task = taskController.get(id)
            if (task.isEmpty()) {
                return@get call.respondText(
                    "No task with id $id",
                    status = HttpStatusCode.NotFound
                )
            } else {
                call.respond(task)
            }
        }


    }
}

fun Application.configureRouting() {

    routing {
        taskRouting()
    }
}
