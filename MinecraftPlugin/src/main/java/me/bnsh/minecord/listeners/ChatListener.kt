package me.bnsh.minecord.listeners

import me.bnsh.minecord.Main
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.net.InetAddress


class ChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        if (Main.checkGuilIdFileExists()) Client().sendMessage(Options.MESSAGE, event.player.name, event.message)
    }
}