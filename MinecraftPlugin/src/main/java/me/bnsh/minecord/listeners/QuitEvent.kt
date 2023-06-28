package me.bnsh.minecord.listeners

import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class QuitEvent : Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        Client().sendMessage(Options.LOG, event.player.name, "Player ${event.player.name} left the game")
    }
}