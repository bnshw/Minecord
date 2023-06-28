package me.bnsh.minecord.database.models

import me.bnsh.minecord.Main
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

    fun setWhitelist(bool: Boolean) {
        DatabaseController().sqlStatement("UPDATE users SET whitelist = $bool WHERE guild_ID = ${Main.getGuildID()}")
    }

    fun getWhitelist(): Boolean {
        val query = DatabaseController().query("SELECT whitelist FROM users WHERE guild_ID = ${Main.getGuildID()}")

        var messageValue = false

        if (query.next()) {
            messageValue = query.getBoolean("whitelist")
        }

        query.close()
        return messageValue
    }

    fun setMessages(message: Option, bool: Boolean) {
        DatabaseController().sqlStatement("UPDATE users SET $message = $bool WHERE guild_ID = ${Main.getGuildID()}")
    }

    fun getMessages(message: Option): Boolean {
        val query = DatabaseController().query("SELECT $message FROM users WHERE guild_ID = ${Main.getGuildID()}")

        var messageValue = false

        if (query.next()) {
            messageValue = query.getBoolean(message.toString())
        }

        query.close()
        return messageValue
    }
}

enum class Option {
    mc_messages,
    dc_messages
}