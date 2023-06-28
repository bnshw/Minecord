package events

import botInstance
import database.models.Users
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.util.*

class JoinEvent : ListenerAdapter() {
    private var communicationID: Long = 0
    private var roleID: Long = 0

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
        val role: Role
        if (guild.getRolesByName(roleName, true).size == 0) {
            role = createRole(guild, roleName)
            roleID = role.idLong
        } else {
            role = botInstance.getRolesByName(roleName, false).first()
            roleID = role.idLong
        }

        val category =
            guild.getCategoriesByName(categoryName, true).firstOrNull()
                ?: guild.createCategory(categoryName)
                    .complete()

        val whitelistChannel = guild.getTextChannelsByName(channel1Name, true).firstOrNull()
        val communicationChannel = guild.getTextChannelsByName(channel2Name, true).firstOrNull()
        val logsChannel = guild.getTextChannelsByName(channel3Name, true).firstOrNull()

        guild.systemChannel?.sendMessage("> Hello ${guild.name}. \n > Add the \"$roleName\" role to your roles to get started.")
            ?.queue()
        if (whitelistChannel == null || communicationChannel == null || logsChannel == null) {
            GlobalScope.launch {
                if (whitelistChannel == null) {
                    createBotChannel(guild, category, channel1Name)
                }
                if (communicationChannel == null) {
                    createBotChannel(guild, category, channel2Name)
                    Users().setUser(guild.idLong, communicationID)
                }
                if (logsChannel == null) {
                    createBotChannel(guild, category, channel3Name)
                }
            }
            return
        }
        Users().setUser(guild.idLong)
    }

    private suspend fun createBotChannel(guild: Guild, category: Category, name: String) {
        if (name == channel2Name) {
            val communicationIDDeferred = CompletableDeferred<Long>()
            guild.createTextChannel(name)
                .setParent(category)
                .queue { channel ->
                    communicationIDDeferred.complete(channel.idLong)
                }

            communicationID = communicationIDDeferred.await()
        }
        if (name == channel3Name) {
            guild.createTextChannel(name)
                .setParent(category)
                .addRolePermissionOverride(
                    roleID,
                    EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY),
                    null
                )
                .addRolePermissionOverride(guild.publicRole.idLong, null, EnumSet.of(Permission.VIEW_CHANNEL))
                .queue()
            return
        }

        guild.createTextChannel(name)
            .setParent(category)
            .queue()
        return
    }

    private fun createRole(guild: Guild, name: String): Role {
        return guild.createRole()
            .setName(name)
            .setColor(Color.green)
            .complete()
    }
}