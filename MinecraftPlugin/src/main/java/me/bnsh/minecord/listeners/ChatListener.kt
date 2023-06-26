package me.bnsh.minecord.listeners

import me.bnsh.minecord.websocket.Client
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.net.InetAddress


class ChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        Client().sendMessage(event.player.name, event.message)
    }
}