package me.bnsh.minecord.database.models

import me.bnsh.minecord.database.DatabaseController

class Users {
    fun checkGuildIdExists(guildID: Long): Boolean {
        val query = DatabaseController().query("SELECT guild_ID FROM users WHERE guild_ID = $guildID")

        var id: Long = 0

        if (query.next()) {
            id = query.getLong("guild_ID")
        }

        query.close()
        return id != 0L
    }
}