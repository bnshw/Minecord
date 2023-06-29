package me.bnsh.minecord

import me.bnsh.minecord.commands.*
import me.bnsh.minecord.listeners.ChatListener
import me.bnsh.minecord.listeners.JoinListener
import me.bnsh.minecord.listeners.PreLoginListener
import me.bnsh.minecord.listeners.QuitListener
import me.bnsh.minecord.websocket.Client
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {

    companion object {
        lateinit var plugin: JavaPlugin
        var pluginPath: String = ""
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Plugin enabled")
        listenerRegistration()
        commandRegistration()
        Client().receiveMessage()
        plugin = this
        pluginPath = dataFolder.path
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
        manager.registerEvents(QuitListener(), this)
    }

    private fun commandRegistration() {
        getCommand("health")?.setExecutor(HealthCommand())
        getCommand("id")?.setExecutor(IdCommand())

        getCommand("receive")?.setExecutor(ReceiveCommand())
        getCommand("receive")?.tabCompleter = TabCompleter()

        getCommand("whitelist")?.setExecutor(WhitelistCommand())
        getCommand("whitelist")?.tabCompleter = TabCompleter()
    }
}
