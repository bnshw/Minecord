package commands.classes

import database.models.Users
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class SetCommand {
    fun onSetCommand(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "ip-address" -> setIpAddress(event)
            "communication-id" -> setCommunicationId(event)
        }
    }

    private fun setIpAddress(event: SlashCommandInteractionEvent) {
        val ip = event.getOption("ip-address")?.asString
        event.guild?.let { Users().setAddressFromGuild(it.idLong, ip) }
    }

    private fun setCommunicationId(event: SlashCommandInteractionEvent) {
        val channelID = event.getOption("communication-id")?.asString
        event.guild?.let { Users().setAddressFromGuild(it.idLong, channelID) }
    }
}
