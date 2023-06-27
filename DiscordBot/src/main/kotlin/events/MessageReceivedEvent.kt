package events

import database.models.Option
import database.models.Users
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import websocket.Client
import websocket.Options

class MessageReceivedEvent : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.author.isBot) return
        if (event.channel.name == "communication" && Users().getMessages(event.guild.idLong, Option.dc_messages)) {
            Client().sendMessage(Options.MESSAGE, event.author.name, event.message.contentRaw)
        }
    }
}