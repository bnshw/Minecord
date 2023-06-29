package me.bnsh.minecord.listeners

import me.bnsh.minecord.Utils
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!Utils().checkGuildIdFileExists(player)) return

        Client().sendMessage(Options.LOG, player.name, "Player ${player.name} joined server")
    }
}