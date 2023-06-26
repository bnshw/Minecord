package me.bnsh.minecord.listeners

import me.bnsh.minecord.Main
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "${ChatColor.AQUA} Hello ${player.name}"

        if (!Main.checkGuilIdFileExists()) {
            Bukkit.broadcast(TextComponent("${ChatColor.RED}Guild-ID has not been set."))

            val clickableMessage = TextComponent("${ChatColor.RED}/id <guild-id>")
            clickableMessage.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/id ")
            player.sendMessage(clickableMessage)
            // clickable event
        }
    }
}