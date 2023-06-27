package me.bnsh.minecord.commands

import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Option
import me.bnsh.minecord.database.models.Users
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ReceiveCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        val player: Player = p0 as Player
        if (p3?.size != 1) {
            Utils().playerMessage(player, "Format: /receive <discord-messages or minecraft-messages>", ChatColor.RED)
            return true
        }
        when (p3[0]) {
            "discord-messages" -> setDiscordMessages(player)
            "minecraft-messages" -> setMinecraftMessages(player)
        }
        return true
    }

    private fun setDiscordMessages(player: Player) {
        if (Users().getMessages(Option.dc_messages)) {
            Users().setMessages(Option.dc_messages, false)
            Utils().playerMessage(player, "The Minecraft server will not receive Discord messages", ChatColor.GREEN)
            return
        }
        Users().setMessages(Option.dc_messages, true)
        Utils().playerMessage(player, "The Minecraft server will now receive Discord messages", ChatColor.GREEN)
        return
    }

    private fun setMinecraftMessages(player: Player) {
        if (Users().getMessages(Option.mc_messages)) {
            Users().setMessages(Option.mc_messages, false)
            Utils().playerMessage(player, "The Discord server will not receive Minecraft messages", ChatColor.GREEN)
            return
        }
        Users().setMessages(Option.mc_messages, true)
        Utils().playerMessage(player, "The Discord server will now receive Minecraft messages", ChatColor.GREEN)
        return
    }
}