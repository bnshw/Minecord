package commands.classes

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
    fun onWhitelistCommand(event: SlashCommandInteractionEvent) {
        val name: String? = event.getOption("player")?.asString
        val response: String? = getUUID(name);
        if (response == "Couldn't find any profile with name $name") {
            event.reply(response)
            return
        }
        val formatedUUID: String? = formatUUID(response)
        val uuid: UUID = UUID.fromString(formatedUUID)
        // UUID to Plugin via websocket
        event.member?.let { event.guild?.let { it1 -> Whitelist().setPlayer(uuid.toString(), it.effectiveName, it1.id) } }

        //Client().sendMessage(Options.WHITELIST, event.member!!.effectiveName, uuid.toString())
        event.reply("Player $name (UUID: $uuid) added to the whitelist.").queue()
    }

    private fun getUUID(name: String?): String? {
        try {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            val json = url.readText()
            val jsonObject = Json.parseToJsonElement(json).jsonObject

            val errorMessage = jsonObject["errorMessage"]?.jsonPrimitive?.content
            if (errorMessage != null) {
                return errorMessage
            }

            return jsonObject["id"]?.jsonPrimitive?.content
        } catch (e: Exception) {
            val errorMessage = e.message
            println("Fehler beim Abrufen der API: $errorMessage")
            return null
        }
    }

    private fun formatUUID(uuid: String?): String? {
        if (uuid == null || uuid.length != 32) {
            return null // Invalid UUID length
        }

        val builder = StringBuilder(uuid)
        builder.insert(8, '-')
        builder.insert(13, '-')
        builder.insert(18, '-')
        builder.insert(23, '-')

        return builder.toString()
    }
}