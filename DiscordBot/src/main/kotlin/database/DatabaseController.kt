package database

import io.github.cdimascio.dotenv.dotenv
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class DatabaseController {
    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection(dotenv().get("DB_URL"), dotenv().get("DB_USER"), dotenv().get("DB_PASSWORD"))
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

    fun insertOrRemoveOrUpdate(sql: String) {
        val connection = getConnection()
        val statement = connection.createStatement()

        statement.executeUpdate(sql)

        statement.close()
        connection.close()
    }
}