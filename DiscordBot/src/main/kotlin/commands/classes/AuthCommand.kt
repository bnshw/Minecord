package commands.classes

import database.models.Users
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import websocket.Client
import websocket.Options

class AuthCommand {
    fun onAuth(event: SlashCommandInteractionEvent) {
        val option = event.getOption("option")?.asBoolean

        if (option == true) {
            event.member?.let {
                Client().sendMessage(
                    Options.AUTH,
                    it.effectiveName,
                    "${event.guild!!.idLong} confirmed"
                )
            }
            event.reply("> This Discord is now linked to the Minecraft server.").queue()
            Users().setAuthFromGuild(event.guild!!.idLong, 2)
            return
        }
        event.member?.let { Client().sendMessage(Options.AUTH, it.effectiveName, "${event.guild!!.idLong} declined") }
        event.reply("> The request has been declined")
        Users().setAuthFromGuild(event.guild!!.idLong, 0)

    }
}