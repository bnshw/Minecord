package websocket

import Utils
import database.models.Users
import java.text.SimpleDateFormat
import java.util.*

class MessageHandler {

    private val timeFormat = SimpleDateFormat("hh:mm:ss")

    fun messageToCommunication(message: List<String>) {
        Utils().getBotChannelByName("Communication", message[3].toLong())
            ?.sendMessage("**${message[0]}** <${message[2]}> ${message.subList(4, message.size).joinToString(" ")}")
            ?.queue()
    }

    fun authMessage(message: List<String>) {
        val communicationChannel = Utils().getBotChannelByName("Communication", message[3].toLong())
        when (Users().getAuthFromGuild(message[3].toLong())) {
            0 -> {
                communicationChannel
                    ?.sendMessage("> The player ${message[2]} tried to link this Discord server to a Minecraft server.\n> `/auth [true/false]`")
                    ?.queue()
                Users().setAuthFromGuild(message[3].toLong(), 1)
            }

            1 -> {
                communicationChannel
                    ?.sendMessage("> There is already an ongoing authentication")?.queue()
            }

            2 -> {
                communicationChannel
                    ?.sendMessage("> This Discord is already linked to an Minecraft server")?.queue()
            }
        }
    }

    fun messageToLog(message: List<String>) {
        Utils().getBotChannelByName("Minecord-Logs", message[3].toLong())
            ?.sendMessage("**[${timeFormat.format(Date())}]** ${message.subList(4, message.size).joinToString(" ")}")?.queue()
    }
}