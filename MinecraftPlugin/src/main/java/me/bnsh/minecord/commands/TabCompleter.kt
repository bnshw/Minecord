package me.bnsh.minecord.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TabCompleter : TabCompleter {
    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>?): MutableList<String>? {
        val list: MutableList<String> = ArrayList()
        if (p1.name == "receive" && p3?.size == 1) {
            list.add("discord-messages")
            list.add("minecraft-messages")
        }

        return list
    }
}