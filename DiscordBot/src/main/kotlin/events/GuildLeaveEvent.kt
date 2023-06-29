package events

import database.models.Users
import database.models.Whitelist
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import websocket.Client
import websocket.Options

class GuildLeaveEvent : ListenerAdapter() {
    override fun onGuildLeave(event: GuildLeaveEvent) {
        Whitelist().removePlayers(event.guild.idLong)
        Users().removeUser(event.guild.idLong)
        Client().sendMessage(Options.LEAVE, event.guild.idLong.toString(), "")
    }
}