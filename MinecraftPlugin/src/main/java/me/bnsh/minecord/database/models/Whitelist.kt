package me.bnsh.minecord.database.models

import me.bnsh.minecord.database.DatabaseController
import java.util.*

class Whitelist {
    fun checkWhitelist(uuid: UUID, ip: String): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist JOIN users on whitelist.guild_ID = users.guild_ID WHERE uuid = '$uuid' AND users.address = '$ip'")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return !uuidString.isNullOrEmpty()
    }
}