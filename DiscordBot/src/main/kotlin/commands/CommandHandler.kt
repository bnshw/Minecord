package commands

import commands.classes.AuthCommand
import commands.classes.ReceiveCommand
import commands.classes.WhitelistCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class CommandHandler : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "whitelist-add" -> WhitelistCommand().onWhitelistCommand(event)
            "whitelist-remove" -> WhitelistCommand().onWhitelistCommand(event)
            "auth" -> AuthCommand().onAuth(event)
            "receive" -> ReceiveCommand().onReceive(event)
        }
    }

    fun checkCommandChannel(event: SlashCommandInteractionEvent, channelName: String): Boolean {
        if (event.channel.name != channelName) {
            val reply = event.reply(
                "> This command can only be used in the ${
                    event.guild?.getTextChannelsByName(
                        channelName,
                        true
                    )?.first()?.asMention
                } Channel"
            )
            reply.setEphemeral(true).queue()
            return true
        }
        return false
    }
}