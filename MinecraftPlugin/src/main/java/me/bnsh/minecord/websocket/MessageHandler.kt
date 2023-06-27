package me.bnsh.minecord.websocket

import me.bnsh.minecord.Main
import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Whitelist
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.player.PlayerKickEvent
import java.io.File

class MessageHandler {
    fun messageToServer(message: List<String>) {
        Utils().broadcast(
            "${message[0]} ${message[2]}: ${message.subList(3, message.size).joinToString(" ")}",
            ChatColor.BLUE
        )
    }

    fun authMessage(message: List<String>) {
        if (message[4] == "confirmed") {
            Utils().broadcast(
                "This Minecraft Server has been linked to the Discord server (Guild ID: ${message[3]})",
                ChatColor.GREEN
            )
            val file = File("Minecord-GuildID.txt")
            file.createNewFile()
            println(file.path)
            file.writeText(message[3])
            return
        }
        Utils().broadcast("The authentication has been declined by user: ${message[2]}", ChatColor.RED)
    }

    fun whitelistAction(splitMessage: List<String>) {
        val playerName: String = splitMessage[4]
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.name == playerName){
                Bukkit.getScheduler().runTask(
                    Main.plugin,
                    Runnable {
                        onlinePlayer.kick(
                            Component.text("${ChatColor.RED}You have been removed from the whitelist"),
                            PlayerKickEvent.Cause.WHITELIST
                        )
                    }
                )
            }
        }
        Whitelist().removePlayer(playerName)
        return
    }
}