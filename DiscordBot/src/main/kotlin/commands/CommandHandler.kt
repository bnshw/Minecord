package commands

import commands.classes.*
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "whitelist" -> WhitelistCommand().onWhitelistCommand(event)
            "ip" -> IpCommand().onIpCommand(event)
            "id" -> IdCommand().onIdCommand(event)
            "auth" -> AuthCommand().onAuth(event)
        }
    }
}