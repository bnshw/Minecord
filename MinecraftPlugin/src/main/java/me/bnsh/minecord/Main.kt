package me.bnsh.minecord

import me.bnsh.minecord.commands.HealthCommand
import me.bnsh.minecord.commands.IdCommand
import me.bnsh.minecord.commands.ReceiveCommand
import me.bnsh.minecord.listeners.ChatListener
import me.bnsh.minecord.listeners.JoinListener
import me.bnsh.minecord.listeners.PreLoginListener
import me.bnsh.minecord.commands.TabCompleter
import me.bnsh.minecord.websocket.Client
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {

    companion object {
        var pluginPath: String = ""

        fun getGuildID(): String = File("Minecord-GuildID.txt").readText()
        fun checkGuilIdFileExists(): Boolean = File("Minecord-GuildID.txt").exists()
    }

    override fun onEnable() {
        // Plugin startup logic
        logger.info("Plugin enabled")
        listenerRegistration()
        commandRegistration()
        Client().receiveMessage()
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
    }

    private fun commandRegistration() {
        getCommand("health")?.setExecutor(HealthCommand())
        getCommand("id")?.setExecutor(IdCommand())
        getCommand("receive")?.setExecutor(ReceiveCommand())
        getCommand("receive")?.tabCompleter = TabCompleter()
    }
}
