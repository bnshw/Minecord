package database.models

import database.DatabaseController
import java.util.UUID

class Whitelist {
    fun setPlayer(uuid: String, name: String, guildID: String) {
        DatabaseController().sqlStatement("INSERT INTO whitelist (uuid, name, guild_ID) VALUES ('$uuid', '$name', $guildID)")
    }

    fun getUuidFromName(name: String): UUID {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE name = $name")

        lateinit var uuid: UUID

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