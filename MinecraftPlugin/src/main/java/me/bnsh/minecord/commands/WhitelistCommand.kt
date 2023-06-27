package me.bnsh.minecord.commands

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Whitelist
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import java.net.URL
import java.util.*

class WhitelistCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        val player = p0 as Player
        if (p3?.size != 2) {
            Utils().playerMessage(player, "Format: /whitelist <add or remove> <player-name>", ChatColor.RED)
            return true
        }
        when (p3[0]) {
            "add" -> addToWhitelist(p3[1], player)
            "remove" -> removeFromWhitelist(p3[1], player)
        }
        return true
    }

    private fun addToWhitelist(playerName: String, sender: Player) {
        val response: String? = getUUID(playerName)
        if (response == null) {
            Utils().playerMessage(sender, "Couldn't find any profile with name $playerName", ChatColor.RED)
            return
        }

        val formattedUUID: String? = formatUUID(response)
        val uuid: UUID = UUID.fromString(formattedUUID)

        if (Whitelist().checkUUID(uuid)) {
            Utils().playerMessage(sender, "Player $playerName is already whitelisted", ChatColor.DARK_GREEN)
            return
        }

        Whitelist().setPlayer(uuid.toString(), playerName)
        Utils().playerMessage(sender, "Player $playerName (UUID: $uuid) added to the whitelist", ChatColor.GREEN)
    }

    private fun removeFromWhitelist(playerName: String, sender: Player) {
        if (Whitelist().checkPlayerExists(playerName)) {
            for (onlinePlayer in Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.name == playerName) onlinePlayer.kick(Component.text("${ChatColor.RED}You have been removed from the whitelist"), PlayerKickEvent.Cause.WHITELIST)
            }
            Whitelist().removePlayer(playerName)
            Utils().playerMessage(sender, "Player $playerName has been removed from the whitelist", ChatColor.GREEN)
            return
        }
        Utils().playerMessage(sender, "Couldn't find a player whitelisted with this name", ChatColor.RED)
    }

    private fun getUUID(name: String?): String? {
        try {
            val url = URL("https://api.mojang.com/users/profiles/minecraft/$name")
            val json = url.readText()
            val jsonObject = Json.parseToJsonElement(json).jsonObject

            return jsonObject["id"]?.jsonPrimitive?.content ?: return "Couldn't find any profile with name $name"
        } catch (e: Exception) {
            val errorMessage = e.message
            println("Fehler beim Abrufen der API: $errorMessage")
            return null
        }
    }

    private fun formatUUID(uuid: String?): String? {
        if (uuid == null || uuid.length != 32) return null

        val builder = StringBuilder(uuid)
        builder.insert(8, '-')
        builder.insert(13, '-')
        builder.insert(18, '-')
        builder.insert(23, '-')

        return builder.toString()
    }
}