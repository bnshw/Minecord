package me.bnsh.minecord.commands

import me.bnsh.minecord.Main
import me.bnsh.minecord.database.models.Users
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

class IdCommand : CommandExecutor {
    private val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()

    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        val player = p0 as Player
        if (p3?.size != 1 || p3[0].toLongOrNull() == null) {
            getHelpMessage(player)
            return true
        }

        if (p3[0].let { Users().checkGuildIdExists(it.toLong()) }) {
            // Wait for discord approval
            val file = File("${Main.pluginPath}Minecord-GuildID.txt")
            file.createNewFile()
            println(file.path)
            file.writeText(p3[0])
            player.sendMessage("${ChatColor.GREEN} Your server has been linked to the Guild ID: ${p3[0]}")
        } else {
            player.sendMessage("${ChatColor.RED} This Guild ID is not linked to the Minecord Bot. Please add the Bot to your Discord, if you haven't, to continue")
        }

        return true
    }


    private fun getHelpMessage(player: Player) {
        player.sendMessage("${ChatColor.RED}Format: /id <guild-id>")
        val clickableMessage = TextComponent("${ChatColor.RED} Click here to see how to get the guild ID!")
        clickableMessage.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://en.wikipedia.org/wiki/Template:Discord_server")
        player.sendMessage(clickableMessage)
    }
}