package me.bnsh.minecord.websocket

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor

class Client {
    private val client = HttpClient {
        install(WebSockets)
    }

    fun receiveMessage() {
        GlobalScope.launch(Dispatchers.Default) {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/chat") {
                // Auf Nachrichten vom Server warten
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val receivedMessage = frame.readText()

                        println("WebSocket: $receivedMessage")
                        val splitMessage = receivedMessage.split("\\s".toRegex())
                        if (splitMessage[0] == "[MINECRAFT]") break

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
                println("[MINECRAFT] $author $content")
                send("[MINECRAFT] $author $content")
            }
        }
    }
}