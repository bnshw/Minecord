package commands.classes

import Utils
import database.models.Users
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
    private var guildID: Long = 0

    fun onWhitelistCommand(event: SlashCommandInteractionEvent) {
        if (Utils().checkCommandChannel(event, "whitelist")) return

        guildID = event.guild?.idLong!!
        if (event.name == "whitelist") {
            when (event.subcommandName) {
                "enable" -> {
                    enableWhitelist(event)
                    return
                }

                "disable" -> {
                    disableWhitelist(event)
                    return
                }
            }
        }
        if (Users().getWhitelist(guildID)) {
            when (event.name) {
                "whitelist-add" -> addToWhitelist(event)
                "whitelist-remove" -> removeFromWhitelist(event)
            }
            return
        }
        event.reply("> Whitelist is currently disabled. \n > Use `/whitelist enable` to enable the whitelist\"").queue()
    }

    private fun enableWhitelist(event: SlashCommandInteractionEvent) {
        if (Users().getWhitelist(guildID)) {
            event.reply("> Whitelist is already enabled").queue()
            return
        }
        event.reply("> Whitelist is now enabled").queue()
        Users().setWhitelist(guildID, true)
    }

    private fun disableWhitelist(event: SlashCommandInteractionEvent) {
        if (!Users().getWhitelist(guildID)) {
            event.reply("> Whitelist is already disabled").queue()
            return
        }
        event.reply("> Whitelist is now disabled").queue()
        Users().setWhitelist(guildID, false)
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

        if (Whitelist().checkUUID(uuid, guildID)) {
            event.reply("> Player $name is already whitelisted").queue()
            return
        }

        event.member?.let { event.guild?.let { it1 -> Whitelist().setPlayer(uuid.toString(), name!!, it1.id) } }
        event.reply("> Player $name (UUID: $uuid) added to the whitelist.").queue()
    }

    private fun removeFromWhitelist(event: SlashCommandInteractionEvent) {
        name = event.getOption("player")?.asString
        if (Whitelist().checkPlayerExists(name!!, guildID)) {
            Whitelist().removePlayer(name!!, guildID)
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