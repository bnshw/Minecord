package database.models

import database.DatabaseController

class Users {
    fun getChannelFromGuild(guildID: Long): Long {
        val query = DatabaseController().getQuery("SELECT channel_ID FROM users WHERE guild_ID = $guildID")

        var channelID: Long = 0

        if (query.next()) {
            channelID = query.getLong("channel_ID")
        }

        query.close()
        return channelID
    }

    fun getGuildFromChannel(channelID: Long): Long {
        val query = DatabaseController().getQuery("SELECT guild_ID FROM users WHERE channel_ID = $channelID")

        var guildID: Long = 0

        if (query.next()) {
            guildID = query.getLong("guild_ID")
        }

        query.close()
        return guildID
    }
}