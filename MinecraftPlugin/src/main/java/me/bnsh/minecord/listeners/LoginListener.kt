package me.bnsh.minecord.listeners

import me.bnsh.minecord.websocket.MessageHandler
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.*

class LoginListener : Listener {
    @EventHandler
    fun onLogin(event: AsyncPlayerPreLoginEvent) {
        if (MessageHandler.whitelist.contains(event.uniqueId)) {
            event.allow()
        }
        else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "${ChatColor.RED} You are not whitelisted.");
            return;
        }
    }
}