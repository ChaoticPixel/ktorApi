package com.example

import com.example.models.Statuses
import models.Types
import org.jetbrains.exposed.sql.Table


object Tasks : Table() {
    val id = uuid("id")
    val type = enumerationByName("type", 255, Types::class)
    val summary = text("summary")
    val description = text("description")
    val status = enumerationByName("status", 255, Statuses::class)
}

