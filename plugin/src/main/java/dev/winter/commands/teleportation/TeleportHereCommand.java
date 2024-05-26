package dev.winter.commands.teleportation;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    private final WinterCore plugin;

    public TeleportHereCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        if (!sender.hasPermission("wintercore.teleporthere") && !sender.hasPermission("wintercore.admin")) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.CORRECT-USAGE", "")));
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.PLAYER-NOT-FOUND", "")));
            return true;
        }

        target.teleport(player);
        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.TELEPORTED-HERE", "").replace("<player>", target.getName())));
        target.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.TELEPORTED-TO-YOU", "").replace("<player>", player.getName())));
        return true;
    }
}