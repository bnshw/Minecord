package me.bnsh.minecord.commands

import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Users
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class IdCommand : CommandExecutor {

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        val player = p0 as Player

        if (!player.isOp) {
            Utils().playerMessage(player, "This command can only be executed by an operator", ChatColor.RED)
            return true
        }

        if (p3?.size == 0) {
            Utils().playerMessage(player, "Guild-ID of linked Discord server: ${Utils().getGuildID()}")
            return true
        }

        if (p3?.size != 1 || p3[0].toLongOrNull() == null) {
            getHelpMessage(player)
            return true
        }

        if (p3[0].let { Users().checkGuildIdExists(it.toLong()) }) {
            Client().sendMessage(Options.AUTH, player.name, p3[0])
            Utils().playerMessage(player, "Waiting for authentication...\nTo authenticate your request follow the Discord Bots instructions")
        } else {
            Utils().playerMessage(player, "This Guild ID is not linked to the Minecord Bot. Please add the Bot to your Discord, if you haven't, to continue", ChatColor.RED)
        }

        return true
    }


    private fun getHelpMessage(player: Player) {
        Utils().playerMessage(player, "Format: /id <guild-id>", ChatColor.RED)
        val clickableMessage = TextComponent("${ChatColor.RED} Click here to see how to get the guild ID!")
        clickableMessage.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://en.wikipedia.org/wiki/Template:Discord_server")
        Utils().playerMessage(player, clickableMessage)
    }
}