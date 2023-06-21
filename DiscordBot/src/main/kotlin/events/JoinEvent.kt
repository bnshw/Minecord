package events

import database.models.Users
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.w3c.dom.Text

class JoinEvent : ListenerAdapter() {
    override fun onGuildJoin(event: GuildJoinEvent) {
        val guild: Guild = event.guild
        val category = guild.getCategoriesByName("Minecraft-Bot", true).firstOrNull() ?: guild.createCategory("Minecraft-Bot").complete()

        val channels = category.channels
        val whitelistChannel = guild.getTextChannelsByName("whitelist", true).firstOrNull()
        val communicationChannel = guild.getTextChannelsByName("communication", true).firstOrNull()


        if (whitelistChannel == null || communicationChannel == null) {
            val (whitelistID, communicationID) = createBotChannels(guild, category)
            Users().setUser(guild.idLong, communicationID)
            guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address with:\n> /set ip-address [ip-address]")?.queue()
            return
        }
        Users().setUser(guild.idLong)
        guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address and your communication channel id with:\n> /set ip-address [ip-address]\n> /set communication-id [channel-id]")?.queue()
    }

    private fun createBotChannels(guild: Guild, category: Category): Pair<Long, Long> {
        var whitelistID: Long = 0
        var communicationID: Long = 0

        guild.createTextChannel("Whitelist")
            .setParent(category)
            .queue{channel ->
                whitelistID = channel.idLong
            }
        guild.createTextChannel("Communication")
            .setParent(category)
            .queue{channel ->
                communicationID = channel.idLong
            }

        return Pair(whitelistID, communicationID)
    }
}