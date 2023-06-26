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

    fun setAddressFromGuild(guildID: Long, address: String?) {
        DatabaseController().sqlStatement("UPDATE users SET address = '$address' WHERE guild_ID = $guildID")
    }

    fun setChannelFromGuild(guildID: Long, channelID: Long) {
        DatabaseController().sqlStatement("UPDATE users SET channel_ID = '$channelID' WHERE guild_ID = $guildID")
    }

    fun getMessagesFromGuild(guildID: Long, option: MessageOptions): Boolean {
        val query = DatabaseController().query("SELECT $option FROM users WHERE guild_ID = $guildID")

        var messages = false

        if (query.next()) {
            messages = query.getBoolean("$option")
        }

        query.close()
        return messages
    }

    fun getChannelFromAddress(address: String): Long {
        val query = DatabaseController().query("SELECT channel_ID FROM users WHERE address = '$address'")

        var channelID: Long = 0

        if (query.next()) {
            channelID = query.getLong("channel_ID")
        }

        query.close()
        return channelID
    }

    fun setMessages(guildID: Long, bool: Boolean, option: MessageOptions) {
        DatabaseController().sqlStatement("UPDATE users SET $option = $bool WHERE guild_ID = $guildID")
    }
}

enum class MessageOptions {
    mc_messages,
    dc_messages
}