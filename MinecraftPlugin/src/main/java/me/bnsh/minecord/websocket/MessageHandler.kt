package me.bnsh.minecord.websocket

import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.UUID

class MessageHandler {

    companion object {
        var whitelist = ArrayList<UUID>()
    }

    fun messageToServer(message: List<String>) {
        val broadcastMessage = TextComponent(
            "${ChatColor.BLUE} ${message[0]} ${message[2]}: ${
                message.subList(3, message.size).joinToString(" ")
            }"
        )
        Bukkit.broadcast(broadcastMessage)
    }

    fun whitelistUUID(stringUUID: String) {
        var uuid: UUID = UUID.fromString(stringUUID)
        whitelist.add(uuid)
    }
}