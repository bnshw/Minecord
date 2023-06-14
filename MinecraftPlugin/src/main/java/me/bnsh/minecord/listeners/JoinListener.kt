package me.bnsh.minecord.listeners

import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "${ChatColor.AQUA} Hello ${player.name}"
    }
}