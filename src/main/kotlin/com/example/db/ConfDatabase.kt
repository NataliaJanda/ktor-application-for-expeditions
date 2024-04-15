package com.example.db

import com.example.db.People.autoIncrement
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object People : Table() {
    val idPerson = integer("idPerson").autoIncrement().uniqueIndex()
    val namePerson = varchar("namePerson", 128)
    val phoneNumber = long("phoneNumber")
    val expeditionID = integer("expeditionID")
        .references(Expeditions.idExpedition)
    override val primaryKey = PrimaryKey(idPerson)
}

object Expeditions : Table(){
    val idExpedition = integer("idExpedition").autoIncrement().uniqueIndex()
    val nameExpedition = varchar("nameExpedition", 128)
    val mountainName = integer("mountainName")
        .references(Mountains.idMountain)
    override val primaryKey = PrimaryKey(Expeditions.idExpedition)

}

object Mountains : Table(){
    val idMountain = integer("idMountain").autoIncrement().uniqueIndex()
    val nameMountain = varchar("nameMountain",128)
    val elevation = varchar("elevation", 128)

    override val primaryKey = PrimaryKey(Mountains.idMountain)

}
fun confDatabse(config: ApplicationConfig) {
    val driverClassName = config.property("storage.driverClassName").getString()
    val jdbcURL = config.property("storage.jdbcURL").getString()
    val database = Database.connect(jdbcURL, driverClassName)
    transaction(database) {
        SchemaUtils.create(People) // == CREATE TABLE
        SchemaUtils.create(Expeditions)
        SchemaUtils.create(Mountains)
    }
}