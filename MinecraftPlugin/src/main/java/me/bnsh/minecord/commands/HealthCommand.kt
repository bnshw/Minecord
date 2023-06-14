package me.bnsh.minecord.commands

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HealthCommand : CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): Boolean {
        val player = p0 as Player

        when (p3?.size) {
            1 -> setHealth(p3[0], player)
            2 -> setTargetHealth(p3[1], p3[0])
        }
        return false
    }

    private fun setHealth(hearts: String, player: Player?) {
        if (hearts.toDoubleOrNull() == null  && hearts.toDouble() <= 20 && hearts.toDouble() >= 0) {
            player?.sendMessage("${ChatColor.RED} /health <hearts> \n Max. 20 hearts")
            return
        }
        player?.health = hearts.toDouble()
        player?.sendMessage("${ChatColor.GOLD} ${player.name} hearts set to ${hearts}")
    }

    private fun setTargetHealth(hearts: String, target: String) {
        val player = Bukkit.getPlayer(target)
        setHealth(hearts, player)
    }
}