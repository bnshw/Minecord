package me.bnsh.minecord

import me.bnsh.minecord.commands.HealthCommand
import me.bnsh.minecord.listeners.ChatListener
import me.bnsh.minecord.listeners.JoinListener
import me.bnsh.minecord.listeners.PreLoginListener
import me.bnsh.minecord.websocket.Client
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Plugin enabled")
        listenerRegistration()
        commandRegistration()
        Client().receiveMessage()
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("Plugin disabled")
    }

    private fun listenerRegistration() {
        val manager = Bukkit.getPluginManager()
        manager.registerEvents(JoinListener(), this)
        manager.registerEvents(ChatListener(), this)
        manager.registerEvents(PreLoginListener(), this)
    }

    private fun commandRegistration() {
        getCommand("health")?.setExecutor(HealthCommand())
    }
}
