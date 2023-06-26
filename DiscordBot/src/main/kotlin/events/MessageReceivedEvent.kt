package events

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import websocket.Client
import websocket.Options

class MessageReceivedEvent : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.message.author.isBot) return
        if (event.channel.name == "communication") {
            Client().sendMessage(Options.MESSAGE, event.author.name, event.message.contentRaw)
        }
    }
}