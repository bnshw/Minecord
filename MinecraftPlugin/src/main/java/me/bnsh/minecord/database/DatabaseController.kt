package me.bnsh.minecord.database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DatabaseController {
    private fun getConnection(): Connection {
        return try {
            DriverManager.getConnection("jdbc:mysql://localhost:3306/minecord", "root", "")
        } catch (e: SQLException) {
            e.printStackTrace()
            throw e
        }
    }

    fun query(sql: String): ResultSet {
        val connection = getConnection()
        return connection.createStatement()
            .executeQuery(sql)
    }

    fun sqlStatement(sql: String) {
        val connection = getConnection()
        val statement = connection.createStatement()

        statement.executeUpdate(sql)

        statement.close()
        connection.close()
    }
}