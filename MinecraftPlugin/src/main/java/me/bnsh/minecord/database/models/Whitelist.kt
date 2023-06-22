package me.bnsh.minecord.database.models

import me.bnsh.minecord.database.DatabaseController
import java.util.*

class Whitelist {
    fun checkUUID(uuid: UUID): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE uuid = '$uuid'")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return !uuidString.isNullOrEmpty()
    }
}