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

    override fun onGuildJoin(event: GuildJoinEvent) {
        botSetup(event.guild)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun botSetup(guild: Guild) {
        if (guild.getRolesByName("Minecord-Mod", true).size == 0) {
            createRole(guild, "Minecord-Mod", "#7cbd6b")
        }

        val category =
            guild.getCategoriesByName("Minecord-Channels", true).firstOrNull()
                ?: guild.createCategory("Minecord-Channels")
                    .complete()

        val whitelistChannel = guild.getTextChannelsByName("whitelist", true).firstOrNull()
        val communicationChannel = guild.getTextChannelsByName("communication", true).firstOrNull()
        val logsChannel = guild.getTextChannelsByName("minecord-logs", true).firstOrNull()

        if (whitelistChannel == null || communicationChannel == null || logsChannel == null) {
            GlobalScope.launch {
                if (whitelistChannel == null) {
                    createBotChannel(guild, category, "whitelist")
                }
                if (communicationChannel == null) {
                    createBotChannel(guild, category, "communication")
                    guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address with:\n> /ip [ip-address]")
                    Users().setUser(guild.idLong, communicationID)
                }
                if (logsChannel == null) {
                    createBotChannel(guild, category, "Minecord-Logs")
                }
            }
            return
        }
        Users().setUser(guild.idLong)
        guild.systemChannel?.sendMessage("Hello ${guild.name}. Please set your server ip address and your communication channel id with:\n> `/ip [ip-address]`\n> `/id [channel-id]`\nAdd the \"Minecord-Mod\" role to your roles to get started.")
            ?.queue()
    }

    private suspend fun createBotChannel(guild: Guild, category: Category, name: String) {
        if (name != "communication") {
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

    private fun createRole(guild: Guild, name: String, color: String): Role {
        return guild.createRole()
            .setName(name)
            .setColor(Color.green)
            .complete()
    }
}