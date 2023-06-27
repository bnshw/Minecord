package database.models

import database.DatabaseController
import java.util.UUID

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
        return !dbName.isNullOrEmpty()
    }

    fun checkUUID(uuid: UUID, guildID: Long): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE uuid = '$uuid' AND guild_ID = $guildID")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return !uuidString.isNullOrEmpty()
    }


    fun getUuidFromName(name: String): UUID? {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE name = '$name'")

        var uuid: UUID? = null

        while (query.next()) {
            uuid = UUID.fromString(query.getString("uuid"))
        }

        query.close()
        return uuid
    }

    fun getGuildFromUUID(uuid: UUID): Long {
        val query = DatabaseController().query("SELECT guild_ID FROM whitelist WHERE uuid = $uuid")

        var guildID: Long = 0

        while (query.next()) {
            guildID = query.getLong("guild_ID")
        }

        query.close()
        return guildID
    }
}