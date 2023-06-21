package websocket

import botInstance
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
                    if (splitMessage[0] == "[MINECRAFT]") {
                        botInstance.getTextChannelById("1121053463237890131")
                            ?.sendMessage("${splitMessage[0]} <${splitMessage[1]}> ${splitMessage.subList(2, splitMessage.size).joinToString(" ")}")?.queue()
                    }
                }
            }
        }
    }

    fun sendMessage(option: Options, author: String, content: String) {
        GlobalScope.launch(Dispatchers.IO) {
            client.webSocket(method = HttpMethod.Get, host = "localhost", port = 8080, path = "/chat") {
                send("[DISCORD] $option $author $content")
            }
        }
    }
}

enum class Options {
    MESSAGE,
    WHITELIST
}