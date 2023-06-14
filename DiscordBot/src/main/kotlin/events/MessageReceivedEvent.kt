package events

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageReceivedEvent : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        // Send message to plugin via websocket
    }
}