package commands.classes

import Utils
import database.models.Option
import database.models.Users
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class ReceiveCommand {
    fun onReceive(event: SlashCommandInteractionEvent) {
        if (Utils().checkMemberRole(event)) return
        if (Utils().checkCommandChannel(event, "communication")) return

        when (event.subcommandName) {
            "discord-messages" -> setDiscordMessages(event)
            "minecraft-messages" -> setMinecraftMessages(event)
        }
    }

    private fun setDiscordMessages(event: SlashCommandInteractionEvent) {
        if (event.guild?.let { Users().getMessages(it.idLong, Option.dc_messages) } == true) {
            Users().setMessages(event.guild!!.idLong, Option.dc_messages, false)
            event.reply("> The Minecraft server will not receive Discord messages").queue()
            return
        }
        Users().setMessages(event.guild!!.idLong, Option.dc_messages, true)
        event.reply("> The Minecraft server will now receive Discord messages").queue()
        return
    }

    private fun setMinecraftMessages(event: SlashCommandInteractionEvent) {
        if (event.guild?.let { Users().getMessages(it.idLong, Option.mc_messages) } == true) {
            Users().setMessages(event.guild!!.idLong, Option.mc_messages, false)
            event.reply("> The Discord server will not receive Minecraft messages").queue()
            return
        }
        Users().setMessages(event.guild!!.idLong, Option.mc_messages, true)
        event.reply("> The Discord server will now receive Minecraft messages").queue()
        return
    }
}