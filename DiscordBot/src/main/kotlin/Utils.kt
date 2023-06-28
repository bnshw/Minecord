import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class Utils {
    fun getBotChannelByName(channelName: String, guildID: Long): TextChannel? {
        for (textChannel in botInstance.getTextChannelsByName(channelName, true)) {
            if (textChannel.parentCategory?.name == "Minecord-Channels" && textChannel.guild.idLong == guildID) {
                return textChannel
            }
        }
        return null
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