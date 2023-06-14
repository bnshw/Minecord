package commands.classes

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
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

        val uuid: UUID = UUID.fromString(response)
        // UUID to Plugin via websocket
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
}