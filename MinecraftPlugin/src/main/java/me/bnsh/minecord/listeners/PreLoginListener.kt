package me.bnsh.minecord.listeners

import me.bnsh.minecord.Main
import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Users
import me.bnsh.minecord.database.models.Whitelist
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class PreLoginListener : Listener {
    @EventHandler
    fun onPreLogin(event: AsyncPlayerPreLoginEvent) {
        Client().sendMessage(Options.LOG, event.name, "Player ${event.name} tried to log in")
        if (Users().getWhitelist()) {
            if (Utils().checkGuilIdFileExists()) {
                if (Whitelist().checkUUID(event.uniqueId)) {
                    event.allow()
                    return
                }
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    "${ChatColor.RED} You are not whitelisted."
                )
                return
            }
            event.allow()
            return
        }
        event.allow()
    }
}