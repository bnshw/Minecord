package me.bnsh.minecord.database.models

import me.bnsh.minecord.Main
import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.DatabaseController
import java.util.*
import kotlin.collections.ArrayList

class Whitelist {
    fun setPlayer(uuid: String, name: String) {
        DatabaseController().sqlStatement("INSERT INTO whitelist (uuid, name, guild_ID) VALUES ('$uuid', '$name', ${Utils().getGuildID()})")
    }

    fun removePlayer(name: String) {
        DatabaseController().sqlStatement("DELETE FROM whitelist WHERE guild_ID = ${Utils().getGuildID()} AND name = '$name'")
    }

    fun checkUUID(uuid: UUID): Boolean {
        val query = DatabaseController().query("SELECT uuid FROM whitelist WHERE uuid = '$uuid' AND guild_ID = ${Utils().getGuildID()}")

        var uuidString = ""

        if (query.next()) {
            uuidString = query.getString("uuid")
        }

        query.close()
        return uuidString.isNotEmpty()
    }

    fun getAllNames(): MutableList<String> {
        val query = DatabaseController().query("SELECT name FROM whitelist WHERE guild_ID = ${Utils().getGuildID()}")

        val nameList: MutableList<String> = ArrayList()

        while (query.next()) {
            nameList.add(query.getString("name"))
        }

        query.close()
        return nameList
    }

    fun checkPlayerExists(name: String): Boolean {
        val query = DatabaseController().query("SELECT name FROM whitelist WHERE name = '$name' AND guild_ID = ${Utils().getGuildID()}")

        var dbName = ""

        if (query.next()) {
            dbName = query.getString("name")
        }

        query.close()
        return dbName.isNotEmpty()
    }
}