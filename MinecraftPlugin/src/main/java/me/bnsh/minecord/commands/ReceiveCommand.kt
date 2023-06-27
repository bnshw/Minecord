package me.bnsh.minecord.commands

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
            player.sendMessage("${ChatColor.RED}Format: /receive <discord-messages or minecraft-messages>")
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
            player.sendMessage("The Minecraft server will not receive Discord messages")
            return
        }
        Users().setMessages(Option.dc_messages, true)
        player.sendMessage("The Minecraft server will now receive Discord messages")
        return
    }

    private fun setMinecraftMessages(player: Player) {
        if (Users().getMessages(Option.mc_messages)) {
            Users().setMessages(Option.mc_messages, false)
            player.sendMessage("The Discord server will not receive Minecraft messages")
            return
        }
        Users().setMessages(Option.mc_messages, true)
        player.sendMessage("The Discord server will now receive Minecraft messages")
        return
    }
}