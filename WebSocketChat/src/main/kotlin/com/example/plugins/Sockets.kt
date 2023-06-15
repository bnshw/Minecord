package com.example.plugins

import com.example.Connection
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.Collections

fun Application.configureSockets() {
    install(WebSockets) {
        // ...
    }
    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat") {
            println("Adding user!")
            val thisConnection = Connection(this)
            connections += thisConnection
            var firstMessage = true
            try {
                send("You are connected! There are ${connections.count()} users here.\nPlease set username:")

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    if (firstMessage) {
                        thisConnection.name = frame.readText()
                        firstMessage = false
                    }
                    else {
                        val receivedText = frame.readText()
                        val textWithUsername = "[${thisConnection.name}]: $receivedText"
                        connections.forEach {
                            it.session.send(textWithUsername)
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection")
                connections -= thisConnection
            }
        }
    }
}