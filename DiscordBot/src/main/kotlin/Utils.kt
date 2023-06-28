import net.dv8tion.jda.api.entities.channel.concrete.TextChannel

class Utils {
    fun getBotChannelByName(channelName: String, guildID: Long): TextChannel? {
        for (textChannel in botInstance.getTextChannelsByName(channelName, true)) {
            if (textChannel.parentCategory?.name == "Minecord-Channels" && textChannel.guild.idLong == guildID) {
                return textChannel
            }
        }
        return null
    }
}