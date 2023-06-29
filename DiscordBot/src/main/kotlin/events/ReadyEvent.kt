package events

import botInstance
import database.models.Users
import database.models.Whitelist
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import websocket.Client
import websocket.Options

class ReadyEvent : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        event.jda.getGuildById("1118104988070195251")?.getTextChannelById("1118104989018116109")
            ?.sendMessage("Bot is ready")?.queue()

        botInstance.guilds.forEach { guild ->
            if (!Users().getAllGuilds().contains(guild.idLong)) JoinEvent().botSetup(guild)
        }

        for (id in Users().getAllGuilds()) {
            var temp = 0
            println("DB ID: $id")
            for (guild in botInstance.guilds) {
                if (guild.idLong != id) temp++
                println("GUILD ID: ${guild.idLong}")
            }
            if (temp == botInstance.guilds.size) { // Erst nach allen durchgängen removen
                Whitelist().removePlayers(id)
                Users().removeUser(id)
                Client().sendMessage(Options.LEAVE, id.toString(), "")
            }
        }
    }
}