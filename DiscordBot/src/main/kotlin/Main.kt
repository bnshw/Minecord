import commands.CommandHandler
import events.JoinEvent
import events.MessageReceivedEvent
import events.ReadyEvent
import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import websocket.Client

lateinit var botInstance: JDA

fun main() {
    val dotenv = dotenv()
    val bot: JDA = JDABuilder.createDefault(dotenv.get("BOT_TOKEN"))
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .addEventListeners(
            CommandHandler(),
            JoinEvent(),
            MessageReceivedEvent(),
            ReadyEvent()
        )
        .build()

    bot.updateCommands().addCommands(
        Commands.slash("whitelist", "Whitelist given player")
            .addOption(OptionType.STRING, "player", "Player name", true)
    ).queue()
    botInstance = bot
    Client().receiveMessage()
}


