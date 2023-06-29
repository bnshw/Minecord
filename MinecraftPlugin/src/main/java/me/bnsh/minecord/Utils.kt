package me.bnsh.minecord

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.io.File

@Suppress("DEPRECATION")
class Utils {
    private val prefix = "${ChatColor.BOLD}[MINECORD]${ChatColor.RESET}"

    fun broadcast(message: String) {
        Bukkit.broadcast(TextComponent("$prefix $message"))
    }

    fun broadcast(message: String, color: ChatColor) {
        Bukkit.broadcast(TextComponent("$prefix $color $message"))
    }

    fun playerMessage(player: Player, message: String) {
        player.sendMessage("$prefix $message")
    }

    fun playerMessage(player: Player, message: String, color: ChatColor) {
        player.sendMessage("$prefix $color $message")
    }

    fun playerMessage(player: Player, message: TextComponent) {
        message.text = "$prefix ${message.text}"
        player.sendMessage(message)
    }

    fun getGuildID(): String = File("Minecord-GuildID.txt").readText()

    fun checkGuildIdFileExists(): Boolean = File("Minecord-GuildID.txt").exists()

    fun checkGuildIdFileExists(player: Player): Boolean {
        val fileExists = File("Minecord-GuildID.txt").exists()
        if (!fileExists) {
            val clickableMessage = TextComponent("${ChatColor.RED}Guild-ID has not been set. (Only operators can set the ID)\n/id <guild-id>")
            clickableMessage.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/id ")
            playerMessage(player, clickableMessage)
        }
        return fileExists
    }
}