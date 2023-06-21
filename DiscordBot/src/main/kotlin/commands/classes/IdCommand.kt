package commands.classes

import database.models.Users
import kotlinx.coroutines.channels.Channel
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class IdCommand {
    fun onIdCommand(event: SlashCommandInteractionEvent) {
        val channel = event.getOption("channel")?.asChannel

        if (channel?.name == "communication") {
            event.guild?.let { Users().setChannelFromGuild(it.idLong, channel.id.toLong()) }
            event.reply("> The ID of the ${channel.asMention} channel (ID: ${channel.id}) has been set in the bot").queue()
            println("communication")
            return
        }

        if (channel?.name == "whitelist") {
            // Update whitelist id in db
            event.reply("> The ID of the ${channel.asMention} channel (ID: ${channel.id}) has been set in the bot").queue()
            println("whitelist")
            return
        }

        event.reply("> The channel has to be either ${event.guild?.getTextChannelsByName("communication", true)?.first()?.asMention} or ${event.guild?.getTextChannelsByName("whitelist", true)?.first()?.asMention}").queue()
        println("none")
    }
}