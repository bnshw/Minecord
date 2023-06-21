package me.bnsh.minecord.listeners

import me.bnsh.minecord.websocket.Client
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatEvent

class ChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        Client().sendMessage(event.player.name, event.message)
    }
}