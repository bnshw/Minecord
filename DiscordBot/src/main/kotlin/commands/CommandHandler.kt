package commands

import commands.classes.AuthCommand
import commands.classes.ReceiveCommand
import commands.classes.WhitelistCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "whitelist" -> WhitelistCommand().onWhitelistCommand(event)
            "whitelist-add" -> WhitelistCommand().onWhitelistCommand(event)
            "whitelist-remove" -> WhitelistCommand().onWhitelistCommand(event)
            "auth" -> AuthCommand().onAuth(event)
            "receive" -> ReceiveCommand().onReceive(event)
        }
    }
}