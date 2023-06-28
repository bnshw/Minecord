package database.models

import database.DatabaseController

class Users {
    fun setUser(guildID: Long, channelID: Long) {
        DatabaseController().sqlStatement("INSERT INTO users (guild_ID, channel_ID) VALUES ($guildID, $channelID)")
    }

    fun setUser(guildID: Long) {
        DatabaseController().sqlStatement("INSERT INTO users (guild_ID) VALUES ($guildID)")
    }

    fun removeUser(guildID: Long) {
        DatabaseController().sqlStatement("DELETE FROM users WHERE guild_ID = $guildID")
    }

    fun setAuthFromGuild(guildID: Long, auth: Int) {
        DatabaseController().sqlStatement("UPDATE users SET auth = $auth WHERE guild_ID = $guildID")
    }

    fun setMessages(guildID: Long, message: Option, bool: Boolean) {
        DatabaseController().sqlStatement("UPDATE users SET $message = $bool WHERE guild_ID = $guildID")
    }

    fun setWhitelist(guildID: Long, bool: Boolean) {
        DatabaseController().sqlStatement("UPDATE users SET whitelist = $bool WHERE guild_ID = $guildID")
    }

    fun getWhitelist(guildID: Long): Boolean {
        val query = DatabaseController().query("SELECT whitelist FROM users WHERE guild_ID = $guildID")

        var messageValue = false

        if (query.next()) {
            messageValue = query.getBoolean("whitelist")
        }

        query.close()
        return messageValue
    }

    fun getMessages(guildID: Long, message: Option): Boolean {
        val query = DatabaseController().query("SELECT $message FROM users WHERE guild_ID = $guildID")

        var messageValue = false

        if (query.next()) {
            messageValue = query.getBoolean(message.toString())
        }

        query.close()
        return messageValue
    }

    fun getAllGuilds(): ArrayList<Long> {
        val query = DatabaseController().query("SELECT guild_ID FROM users")

        val guildList: ArrayList<Long> = ArrayList()

        while (query.next()) {
            guildList.add(query.getLong("guild_ID"))
        }

        query.close()
        return guildList
    }

    fun getAuthFromGuild(guildID: Long): Int {
        val query = DatabaseController().query("SELECT auth FROM users WHERE guild_ID = $guildID")

        var auth = 0

        if (query.next()) {
            auth = query.getInt("auth")
        }

        query.close()
        return auth
    }
}

enum class Option {
    mc_messages,
    dc_messages
}