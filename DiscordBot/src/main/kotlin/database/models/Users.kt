package database.models

import database.DatabaseController

class Users {
    fun setUser(guildID: Long, channelID: Long) {
        DatabaseController().insertOrRemoveOrUpdate("INSERT INTO users (guild_ID, channel_ID) VALUES ($guildID, $channelID)")
    }

    fun setUser(guildID: Long) {
        DatabaseController().insertOrRemoveOrUpdate("INSERT INTO users (guild_ID) VALUES ($guildID)")
    }

    fun removeUser(guildID: Long) {
        DatabaseController().insertOrRemoveOrUpdate("DELETE FROM users WHERE guild_ID = $guildID")
    }

    fun setAddressFromGuild(guildID: Long, address: String?) {
        DatabaseController().insertOrRemoveOrUpdate("UPDATE users SET address = '$address' WHERE guild_ID = $guildID")
    }

    fun setChannelFromGuild(guildID: Long, channelID: Long) {
        DatabaseController().insertOrRemoveOrUpdate("UPDATE users SET channel_ID = '$channelID' WHERE guild_ID = $guildID")
    }

    fun getChannelFromGuild(guildID: Long): Long {
        val query = DatabaseController().query("SELECT channel_ID FROM users WHERE guild_ID = $guildID")

        var channelID: Long = 0

        if (query.next()) {
            channelID = query.getLong("channel_ID")
        }

        query.close()
        return channelID
    }

    fun getGuildFromChannel(channelID: Long): Long {
        val query = DatabaseController().query("SELECT guild_ID FROM users WHERE channel_ID = $channelID")

        var guildID: Long = 0

        if (query.next()) {
            guildID = query.getLong("guild_ID")
        }

        query.close()
        return guildID
    }
}