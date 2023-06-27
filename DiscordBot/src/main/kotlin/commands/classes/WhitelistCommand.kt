package commands.classes

import commands.CommandHandler
import database.models.Whitelist
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import websocket.Client
import websocket.Options
import java.net.URL
import java.util.*

class WhitelistCommand {

    private var name: String? = ""

    fun onWhitelistCommand(event: SlashCommandInteractionEvent) {
        if (CommandHandler().checkCommandChannel(event, "whitelist")) return

        when (event.name) {
            "whitelist-add" -> addToWhitelist(event)
            "whitelist-remove" -> removeFromWhitelist(event)
        }
    }

    private fun addToWhitelist(event: SlashCommandInteractionEvent) {
        name = event.getOption("player")?.asString
        val response: String? = getUUID(name)
        if (response == null) {
            event.reply("> Couldn't find any profile with name $name").queue()
            return
        }

        val formattedUUID: String? = formatUUID(response)
        val uuid: UUID = UUID.fromString(formattedUUID)

        if (event.guild?.let { Whitelist().checkUUID(uuid, it.idLong) } == true) {
            event.reply("> Player $name is already whitelisted").queue()
            return
        }

        event.member?.let { event.guild?.let { it1 -> Whitelist().setPlayer(uuid.toString(), name!!, it1.id) } }
        event.reply("> Player $name (UUID: $uuid) added to the whitelist.").queue()
    }

    private fun removeFromWhitelist(event: SlashCommandInteractionEvent) {
        name = event.getOption("player")?.asString
        if (event.guild?.let { Whitelist().checkPlayerExists(name!!, it.idLong) } == true) {
            event.guild?.let { Whitelist().removePlayer(name!!, it.idLong) }
            event.reply("> Player $name has been removed from the whitelist").queue()
            event.member?.let { Client().sendMessage(Options.WHITELIST, it.effectiveName, "REMOVED $name") }
            return
        }
        event.reply("> Couldn't find a player whitelisted with this name").queue()
    }

    private fun getUUID(name: String?): String? {
        try {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            val json = url.readText()
            val jsonObject = Json.parseToJsonElement(json).jsonObject

            return jsonObject["id"]?.jsonPrimitive?.content ?: return "> Couldn't find any profile with name $name"
        } catch (e: Exception) {
            val errorMessage = e.message
            println("Fehler beim Abrufen der API: $errorMessage")
            return null
        }
    }

    private fun formatUUID(uuid: String?): String? {
        if (uuid == null || uuid.length != 32) return null

        val builder = StringBuilder(uuid)
        builder.insert(8, '-')
        builder.insert(13, '-')
        builder.insert(18, '-')
        builder.insert(23, '-')

        return builder.toString()
    }
}