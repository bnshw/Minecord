package events

import database.models.MessageOptions
import database.models.Users
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import websocket.Client
import websocket.Options

class MessageReceivedEvent : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.author.isBot) return
        if (event.channel.name == "communication" && Users().getMessagesFromGuild(event.guild.idLong, MessageOptions.dc_messages)) {
            Client().sendMessage(Options.MESSAGE, event.author.name, event.message.contentRaw)
        }
    }
}