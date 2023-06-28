package me.bnsh.minecord

import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Utils {
    private val prefix = "[MINECORD]"

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
}