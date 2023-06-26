import commands.CommandHandler
import database.models.Users

import events.GuildLeaveEvent
import events.JoinEvent
import events.MessageReceivedEvent
import events.ReadyEvent
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.interactions.commands.Command.Subcommand
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData
import net.dv8tion.jda.api.requests.GatewayIntent
import websocket.Client

lateinit var botInstance: JDA
lateinit var guildList: MutableList<Guild>
var dotenv = dotenv {
    directory = "../../Minecord"
    filename = ".env"
}

fun main() {
    val bot: JDA = JDABuilder.createDefault(dotenv.get("BOT_TOKEN"))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(
            CommandHandler(),
            JoinEvent(),
            MessageReceivedEvent(),
            ReadyEvent(),
            GuildLeaveEvent()
        )
        .build()

    val addData = SubcommandData("add", "Add a player to the whitelist")
    val removeData = SubcommandData("remove", "Remove a player from the whitelist")

    bot.updateCommands().addCommands(
        Commands.slash("whitelist", "Whitelist given player")
            .addOption(OptionType.STRING, "option", "Add or Remove")
            .addOption(OptionType.STRING, "player", "Player name"),
        Commands.slash("ip", "Sets the Minecraft IP address")
            .addOption(OptionType.STRING, "ip", "IP address of the server", true),
        Commands.slash("id", "Sets the communication or whitelist id")
            .addOption(OptionType.CHANNEL, "channel", "Communication or whitelist channel", true),
        Commands.slash("auth", "Sets authentication")
            .addOption(OptionType.BOOLEAN, "option", "true/false", true),
        ).queue()
    botInstance = bot

    bot.guilds.forEach { guild ->
        println(guild.idLong)
        if (Users().getAllGuilds().contains(guild.idLong)) JoinEvent().botSetup(guild)
    }

    Client().receiveMessage()
}


