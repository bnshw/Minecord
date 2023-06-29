package me.bnsh.minecord.websocket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import me.bnsh.minecord.Main
import me.bnsh.minecord.Utils

class Client {
    private val client = HttpClient {
        install(WebSockets)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun receiveMessage() {
        GlobalScope.launch(Dispatchers.Default) {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/chat") {
                // Auf Nachrichten vom Server warten
                while(true) {
                    val othersMessage = incoming.receive() as? Frame.Text ?: continue
                    println(othersMessage.readText())
                    val splitMessage = othersMessage.readText().split("\\s".toRegex())
                    if (splitMessage[0] == "[DISCORD]") {
                        when (splitMessage[1]) {
                            "MESSAGE" -> MessageHandler().messageToServer(splitMessage)
                            "AUTH" -> MessageHandler().authMessage(splitMessage)
                            "WHITELIST" -> MessageHandler().whitelistAction(splitMessage)
                            "LEAVE" -> MessageHandler().deleteGuildIdFile(splitMessage)
                        }
                    }
                }
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendMessage(option: Options, author: String, content: String) {
        GlobalScope.launch(Dispatchers.IO) {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/chat") {
                when (option) {
                    Options.AUTH -> send("[MINECRAFT] $option $author $content")
                    else -> send("[MINECRAFT] $option $author ${Utils().getGuildID()} $content")
                }
            }
        }
    }
}

enum class Options {
    MESSAGE,
    AUTH,
    LOG,
}