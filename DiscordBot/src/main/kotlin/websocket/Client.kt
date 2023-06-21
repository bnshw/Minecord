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
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val receivedMessage = frame.readText()

                        println("Nachricht empfangen: $receivedMessage")
                        val splitMessage = receivedMessage.split("\\s".toRegex())
                        if (splitMessage[0] == "[DISCORD]") break
                        botInstance.getTextChannelById("1118497382934511656")
                            ?.sendMessage("${splitMessage[0]} ${splitMessage[1]}: ${splitMessage.subList(2, splitMessage.size).joinToString(" ")}")?.queue()
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










