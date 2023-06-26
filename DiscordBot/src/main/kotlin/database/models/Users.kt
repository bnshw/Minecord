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

    fun setAuthFromGuild(guildID: Long, auth: Int) {
        DatabaseController().sqlStatement("UPDATE users SET auth = $auth WHERE guild_ID = $guildID")
    }

    fun getAllGuilds(): ArrayList<Long> {
        val query = DatabaseController().query("SELECT guild_ID FROM users")

        var guildList: ArrayList<Long> = ArrayList()

        while (query.next()) {
            guildList.add(query.getLong("guild_ID"))
        }

        query.close()
        return guildList
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

    fun getAuthFromGuild(guildID: Long): Int {
        val query = DatabaseController().query("SELECT auth FROM users WHERE guild_ID = $guildID")

        var auth: Int = 0

        if (query.next()) {
            auth = query.getInt("auth")
        }

        query.close()
        return auth
    }
}