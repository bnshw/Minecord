package events

import database.models.Users
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildLeaveEvent : ListenerAdapter() {
    override fun onGuildLeave(event: GuildLeaveEvent) {
        Users().removeUser(event.guild.idLong)
    }
}