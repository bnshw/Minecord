package me.bnsh.minecord.websocket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*

class Client {
    private val client = HttpClient {
        install(WebSockets)
    }

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
                            "WHITELIST" -> MessageHandler().whitelistUUID(splitMessage[3])
                        }
                    }
                }
            }
        }
    }

    fun sendMessage(author: String, content: String) {
        GlobalScope.launch(Dispatchers.IO) {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/chat") {
                send("[MINECRAFT] $author $content")
            }
        }
    }
}