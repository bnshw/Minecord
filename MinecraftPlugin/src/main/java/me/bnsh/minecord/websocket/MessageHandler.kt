package me.bnsh.minecord.websocket

import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.File

class MessageHandler {
    fun messageToServer(message: List<String>) {
        val broadcastMessage = TextComponent(
            "${ChatColor.BLUE} ${message[0]} ${message[2]}: ${
                message.subList(3, message.size).joinToString(" ")
            }"
        )
        Bukkit.broadcast(broadcastMessage)
    }

    fun authMessage(message: List<String>) {
        if (message[4] == "confirmed") {
            Bukkit.broadcast(TextComponent("${ChatColor.GREEN}This Minecraft Server has been linked to the Discord server (Guild ID: ${message[3]})."))
            val file = File("Minecord-GuildID.txt")
            file.createNewFile()
            println(file.path)
            file.writeText(message[3])
            return
        }
        Bukkit.broadcast(TextComponent("${ChatColor.RED}The authentication has been declined by user: ${message[2]}."))
    }
}