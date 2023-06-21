package database.models

import database.DatabaseController
import java.util.UUID

class Whitelist {
    fun getUuidFromName(name: String): UUID {
        val query = DatabaseController().getQuery("SELECT uuid FROM whitelist WHERE name = $name")

        lateinit var uuid: UUID

        while (query.next()) {
            uuid = UUID.fromString(query.getString("uuid"))
        }

        query.close()
        return uuid
    }

    fun getGuildFromUUID(uuid: UUID): Long {
        val query = DatabaseController().getQuery("SELECT guild_ID FROM whitelist WHERE uuid = $uuid")

        var guildID: Long = 0

        while (query.next()) {
            guildID = query.getLong("guild_ID")
        }

        query.close()
        return guildID
    }
}