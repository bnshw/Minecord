package database.models

import database.DatabaseController
import java.util.*

class Whitelist {
    fun setPlayer(uuid: String, name: String, guildID: String) {
        DatabaseController().sqlStatement("INSERT INTO whitelist (uuid, name, guild_ID) VALUES ('$uuid', '$name', $guildID)")
    }

    fun removePlayers(guildID: Long) {
        DatabaseController().sqlStatement("DELETE FROM whitelist WHERE guild_ID = $guildID")
    }

    fun removePlayer(name: String, guildID: Long) {
        DatabaseController().sqlStatement("DELETE FROM whitelist WHERE guild_ID = $guildID AND name = '$name'")
    }

    fun checkPlayerExists(name: String, guildID: Long): Boolean {
        val query = DatabaseController().query("SELECT name FROM whitelist WHERE name = '$name' AND guild_ID = $guildID")

        var dbName = ""

        if (query.next()) {
            dbName = query.getString("name")
        }

        query.close()
        return dbName.isNotEmpty()
    }

    fun checkUUID(uuid: UUID, guildID: Long): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE uuid = '$uuid' AND guild_ID = $guildID")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return uuidString.isNotEmpty()
    }
}