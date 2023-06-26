package events

import database.models.Users
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JoinEvent : ListenerAdapter() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onGuildJoin(event: GuildJoinEvent) {
        botSetup(event.guild)
    }

    fun botSetup(guild: Guild) {
        val category =
            guild.getCategoriesByName("Minecraft-Bot", true).firstOrNull() ?: guild.createCategory("Minecraft-Bot")
                .complete()

        val whitelistChannel = guild.getTextChannelsByName("whitelist", true).firstOrNull()
        val communicationChannel = guild.getTextChannelsByName("communication", true).firstOrNull()


        if (whitelistChannel == null || communicationChannel == null) {
            GlobalScope.launch {
                val (whitelistID, communicationID) = createBotChannels(guild, category)
                Users().setUser(guild.idLong, communicationID)
                guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address with:\n> /ip [ip-address]")
                    ?.queue()
            }
            return
        }
        Users().setUser(guild.idLong)
        guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address and your communication channel id with:\n> /ip [ip-address]\n> /id [channel-id]")
            ?.queue()
    }

    private suspend fun createBotChannels(guild: Guild, category: Category): Pair<Long, Long> {
        val whitelistIDDeferred = CompletableDeferred<Long>()
        val communicationIDDeferred = CompletableDeferred<Long>()

        guild.createTextChannel("Whitelist")
            .setParent(category)
            .queue { channel ->
                whitelistIDDeferred.complete(channel.idLong)
            }
        guild.createTextChannel("Communication")
            .setParent(category)
            .queue { channel ->
                communicationIDDeferred.complete(channel.idLong)
            }

        val whitelistID = whitelistIDDeferred.await()
        val communicationID = communicationIDDeferred.await()
        return Pair(whitelistID, communicationID)
    }
}