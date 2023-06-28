package me.bnsh.minecord.listeners

import me.bnsh.minecord.Main
import me.bnsh.minecord.Utils
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "${ChatColor.AQUA} Hello ${player.name}"
        Client().sendMessage(Options.LOG, player.name, "Player ${player.name} joined server")

        if (!Main.checkGuilIdFileExists()) {
            Utils().playerMessage(player,"Guild-ID has not been set.", ChatColor.RED)

            val clickableMessage = TextComponent("${ChatColor.RED}/id <guild-id>")
            clickableMessage.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/id ")
            Utils().playerMessage(player, clickableMessage)
        }
    }
}