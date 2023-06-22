package me.bnsh.minecord.listeners

import me.bnsh.minecord.database.models.Whitelist
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class PreLoginListener : Listener {
    @EventHandler
    fun onPreLogin(event: AsyncPlayerPreLoginEvent) {
        if (Whitelist().checkWhitelist(event.uniqueId, event.address.toString().replace("/", ""))) {
            event.allow()
            return
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "${ChatColor.RED} You are not whitelisted.")

        /*
        if (MessageHandler.whitelist.contains(event.uniqueId)) {
            event.allow()
        }
        else {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "${ChatColor.RED} You are not whitelisted.")
            return
        }
        */
    }
}