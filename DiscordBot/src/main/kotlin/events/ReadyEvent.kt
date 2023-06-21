package events

import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReadyEvent : ListenerAdapter() {
    override fun onReady(event: ReadyEvent) {
        event.jda.getGuildById("1118104988070195251")?.getTextChannelById("1118104989018116109")?.sendMessage("Bot is ready")?.queue()
    }
}