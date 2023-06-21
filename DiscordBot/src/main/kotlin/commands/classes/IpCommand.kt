package commands.classes

import database.models.Users
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class IpCommand {
    fun onIpCommand(event: SlashCommandInteractionEvent) {
        val ip = event.getOption("ip")?.asString
        event.guild?.let { Users().setAddressFromGuild(it.idLong, ip) }
        event.reply("> The IP address of your Minecraft server has been saved").queue()
    }
}