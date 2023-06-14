package events

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.Category
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JoinEvent : ListenerAdapter() {
    override fun onGuildJoin(event: GuildJoinEvent) {
        val guild: Guild = event.guild
        val category = guild.getCategoriesByName("Minecraft-Bot", true).firstOrNull() ?: guild.createCategory("Minecraft-Bot").complete()

        val channels = category.channels
        val whitelistChannel = channels.filterIsInstance<TextChannel>().any { it.name == "Whitelist" }
        val communicationChannel = channels.filterIsInstance<TextChannel>().any { it.name == "Communication" }

        if (!whitelistChannel || !communicationChannel) {
            createBotChannels(guild, category)
        }
    }

    private fun createBotChannels(guild: Guild, category: Category) {
        guild.createTextChannel("Whitelist")
            .setParent(category)
            .queue()
        guild.createTextChannel("Communication")
            .setParent(category)
            .queue()
    }
}