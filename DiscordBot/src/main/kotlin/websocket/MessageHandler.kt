package websocket

import botInstance
import database.models.Users

class MessageHandler {
    fun messageToDiscord(message: List<String>) {
        botInstance.getTextChannelById(Users().getChannelFromGuild(message[3].toLong()))
            ?.sendMessage("${message[0]} <${message[2]}> ${message.subList(4, message.size).joinToString(" ")}")?.queue()
    }

    fun authMessage(message: List<String>) {
        when (Users().getAuthFromGuild(message[3].toLong())) {
            0 -> {
                botInstance.getTextChannelById(Users().getChannelFromGuild(message[3].toLong()))
                    ?.sendMessage("> The player ${message[2]} tried to link this Discord server to a Minecraft server.\n> /auth [true/false]")?.queue()
                Users().setAuthFromGuild(message[3].toLong(), 1)
            }

            1 -> {
                botInstance.getTextChannelById(Users().getChannelFromGuild(message[3].toLong()))
                    ?.sendMessage("> There is already an ongoing authentication")?.queue()
            }

            2 -> {
                botInstance.getTextChannelById(Users().getChannelFromGuild(message[3].toLong()))
                    ?.sendMessage("> This Discord is already linked to an Minecraft server")?.queue()
            }
        }
    }
}