# Minecord - Connect Discord and Minecraft ⛏💬 

***

> **⚠️ Warning: The Discord bot and WebSocket are currently not running!**

This project includes a Discord bot and a WebSocket server that are required for the full functionality of the plugin. Please note that there are currently no active instances of the Discord bot and WebSocket provided.

To use the plugin, you will need to build and configure the project yourself. Follow the instructions in the [Building](#building) section to successfully build the project and configure the necessary settings.

***

Minecord is a plugin for [Minecraft](https://www.minecraft.net/) and a [Discord](https://discord.com/) bot that allows seamless communication between Discord and Minecraft. With Minecord, players can easily send and receive messages between both platforms.

## Features

* **Integrated communication:** Send and receive messages between Discord and Minecraft.
* **Whitelist function:** Manage your whitelist conveniently via Discord or Minecraft and allow access only to selected players.

## Installation
1. Add the Minecord bot to your Discord server by visiting [Bot Authorization](https://discord.com/oauth2/authorize?client_id=1118087884080160778&permissions=8&scope=bot%20applications.commands) and granting the necessary permissions.
2. Download the Minecord plugin from our [GitHub repository](https://github.com/bnshw/Minecord/releases).
3. Add the plugin to your Minecraft server.
4. Obtain the [Guild ID](https://en.wikipedia.org/wiki/Template:Discord_server) of your Discord server.
5. In Minecraft, use the command /id <guild-id> to specify the Guild ID for communication.

## Commands
| Commands  | Platform           | Arguments                            | Descripton                                                                                                              |
|-----------|--------------------|--------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| Whitelist | Discord, Minecraft | add, remove, enable, disable         | Use this command to manage the whitelist. Add, remove players, and enable or disable the whitelist.                     |
| Receive   | Discord, Minecraft | discord-messages, minecraft-messages | Use this command to control message reception between Discord and Minecraft. Enable or disable message exchange.        |
| Id        | Minecraft          | [Guild ID](https://en.wikipedia.org/wiki/Template:Discord_server)                           | Use this command to request the linking of your Minecraft server with the Discord server by providing the Guild ID.     |
| Auth      | Discord            | True, False                          | After requesting to link your Minecraft server using the `/id <guild-id>` command, you need to authorize it in Discord. |

## Building
1. Clone the repository to your local machine.
2. Open a terminal and navigate to the /DiscordBot directory.
3. Create a .env file in the [/DiscordBot](https://github.com/bnshw/Minecord/tree/main/DiscordBot) directory and specify the following environment variables:
```.env
BOT_TOKEN=<your Discord bot token>
DB_URL=jdbc:mysql://<your database URL>
DB_USER=<your database username>
DB_PASSWORD=<your database password>
WS_HOST=<WebSocket server host>
WS_PORT=<WebSocket server port>
WS_PATH=<WebSocket server path>
```
Make sure to update each variable with the appropriate value for your environment.

4. Save the .env file.
5. In the terminal, run the following command to build the project:

```powershell
./gradlew build
```
6. Once the build process is complete, you can find the compiled artifacts in the /build directory.

## License

Minecord is licensed under the MIT License. For more information, see the [license file]([https://github.com/bnshw/Minecord/LICENSE](https://github.com/bnshw/Minecord/blob/main/LICENSE)https://github.com/bnshw/Minecord/blob/main/LICENSE).
