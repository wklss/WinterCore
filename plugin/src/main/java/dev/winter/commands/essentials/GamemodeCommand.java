package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {

    private final WinterCore plugin;
    public GamemodeCommand(WinterCore plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            return false;
        }

        if (!sender.hasPermission("wintercore.gamemode") && !sender.hasPermission("wintercore.admin")) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        GameMode gameMode = parseGameMode(args[0]);
        if (gameMode == null) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("GAMEMODE.INVALID-GAMEMODE", "")));
            return false;
        }

        Player targetPlayer = null;
        if (args.length == 2) {
            targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) {
                sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.PLAYER-NOT-FOUND", "")));
                return true;
            }
        } else if (sender instanceof Player) {
            targetPlayer = (Player) sender;
        } else {
            sender.sendMessage(Parse.parse("<red>You must specify a player from console."));
            return true;
        }

        targetPlayer.setGameMode(gameMode);
        if (sender.equals(targetPlayer)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("GAMEMODE.UPDATED", "").replace("<gamemode>", gameMode.name().toLowerCase())));
        } else {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("GAMEMODE.UPDATED-OTHER-SENDER", "").replace("<player>", targetPlayer.getName()).replace("<gamemode>", gameMode.name().toLowerCase())));
            targetPlayer.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("GAMEMODE.UPDATED-OTHER-TARGET", "").replace("<gamemode>", gameMode.name().toLowerCase())));
        }

        return true;
    }

    private GameMode parseGameMode(String input) {
        switch (input.toLowerCase()) {
            case "survival":
            case "s":
            case "0":
                return GameMode.SURVIVAL;
            case "creative":
            case "c":
            case "1":
                return GameMode.CREATIVE;
            case "adventure":
            case "a":
            case "2":
                return GameMode.ADVENTURE;
            case "spectator":
            case "sp":
            case "3":
                return GameMode.SPECTATOR;
            default:
                return null;
        }
    }
}