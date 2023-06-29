package me.bnsh.minecord.listeners

import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Option
import me.bnsh.minecord.database.models.Users
import me.bnsh.minecord.websocket.Client
import me.bnsh.minecord.websocket.Options
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent


class ChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        if (Utils().checkGuildIdFileExists(event.player) && Users().getMessages(Option.mc_messages)) {
            Client().sendMessage(Options.MESSAGE, event.player.name, event.message)
        }
    }
}