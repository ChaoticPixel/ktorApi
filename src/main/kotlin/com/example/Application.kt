package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.*




fun main() {
    initDB()
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)

}



fun initDB() {
    val config = HikariConfig()
    config.dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
    config.addDataSourceProperty("user", "postgres")
    config.addDataSourceProperty("password", "postgres")
    config.addDataSourceProperty("databaseName", "postgres")
    config.addDataSourceProperty("portNumber", "5432")
    config.addDataSourceProperty("serverName", "127.0.0.1")
    config.schema = "public"
    val ds = HikariDataSource(config)
    Database.connect(ds)

    val flyway = Flyway.configure().dataSource("jdbc:postgresql:postgres", "postgres", "postgres").load()
    flyway.migrate()

}



