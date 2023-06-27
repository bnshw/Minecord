package events

import database.models.Users
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color

class JoinEvent : ListenerAdapter() {
    private var communicationID: Long = 0

    private val roleName: String = "Minecord-Mod"
    private val categoryName: String = "Minecord-Channels"
    private val channel1Name: String = "Whitelist"
    private val channel2Name: String = "Communication"
    private val channel3Name: String = "Minecord-Logs"

    override fun onGuildJoin(event: GuildJoinEvent) {
        botSetup(event.guild)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun botSetup(guild: Guild) {
        if (guild.getRolesByName(roleName, true).size == 0) {
            createRole(guild, roleName)
        }

        val category =
            guild.getCategoriesByName(categoryName, true).firstOrNull()
                ?: guild.createCategory(categoryName)
                    .complete()

        val whitelistChannel = guild.getTextChannelsByName(channel1Name, true).firstOrNull()
        val communicationChannel = guild.getTextChannelsByName(channel2Name, true).firstOrNull()
        val logsChannel = guild.getTextChannelsByName(channel3Name, true).firstOrNull()

        if (whitelistChannel == null || communicationChannel == null || logsChannel == null) {
            GlobalScope.launch {
                if (whitelistChannel == null) {
                    createBotChannel(guild, category, channel1Name)
                }
                if (communicationChannel == null) {
                    createBotChannel(guild, category, channel2Name)
                    guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address with:\n> /ip [ip-address]")
                    Users().setUser(guild.idLong, communicationID)
                }
                if (logsChannel == null) {
                    createBotChannel(guild, category, channel3Name)
                }
            }
            return
        }
        Users().setUser(guild.idLong)
        guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address and your $channel2Name channel id with:\n> `/ip [ip-address]`\n> `/id [channel-id]`\nAdd the \"$roleName\" role to your roles to get started.")
            ?.queue()
    }

    private suspend fun createBotChannel(guild: Guild, category: Category, name: String) {
        if (name != channel2Name) {
            guild.createTextChannel(name)
                .setParent(category)
                .queue()
            return
        }
        val communicationIDDeferred = CompletableDeferred<Long>()
        guild.createTextChannel(name)
            .setParent(category)
            .queue { channel ->
                communicationIDDeferred.complete(channel.idLong)
            }

        communicationID = communicationIDDeferred.await()
    }

    private fun createRole(guild: Guild, name: String): Role {
        return guild.createRole()
            .setName(name)
            .setColor(Color.green)
            .complete()
    }
}