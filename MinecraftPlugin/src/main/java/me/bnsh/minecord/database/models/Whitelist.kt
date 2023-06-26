package me.bnsh.minecord.database.models

import me.bnsh.minecord.Main
import me.bnsh.minecord.database.DatabaseController
import java.util.*

class Whitelist {
    fun checkWhitelist(uuid: UUID): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE uuid = '$uuid' AND guild_ID = ${Main.getGuildID()}")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return !uuidString.isNullOrEmpty()
    }
}