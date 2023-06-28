package websocket

import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Client {

    private val client = HttpClient {
        install(WebSockets)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun receiveMessage() {
        GlobalScope.launch(Dispatchers.Default) {
            client.webSocket(
                method = HttpMethod.Get,
                host = dotenv().get("WS_HOST"),
                port = dotenv().get("WS_PORT").toInt(),
                path = dotenv().get("WS_PATH")
            ) {
                while (true) {
                    val othersMessage = incoming.receive() as? Frame.Text ?: continue
                    println(othersMessage.readText())
                    val splitMessage = othersMessage.readText().split("\\s".toRegex())
                    if (splitMessage[0] == "[MINECRAFT]") {
                        when (splitMessage[1]) {
                            "MESSAGE" -> MessageHandler().messageToCommunication(splitMessage)
                            "AUTH" -> MessageHandler().authMessage(splitMessage)
                            "LOG" -> MessageHandler().messageToLog(splitMessage)
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
                send("[DISCORD] $option $author $content")
            }
        }
    }
}

enum class Options {
    MESSAGE,
    AUTH,
    WHITELIST,
    LOG
}