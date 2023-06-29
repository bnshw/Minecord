package me.bnsh.minecord.commands

import me.bnsh.minecord.Utils
import me.bnsh.minecord.database.models.Whitelist
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

        if (p1.name == "whitelist" && p3?.size == 1) {
            list.add("add")
            list.add("remove")
            list.add("enable")
            list.add("disable")
        }

        if (p1.name == "whitelist" && p3?.size == 2 && p3[0] == "remove") {
            for (name in Whitelist().getAllNames()) {
                list.add(name)
            }
        }

        return list
    }
}