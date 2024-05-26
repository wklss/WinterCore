package dev.winter.commands.teleportation;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportWorldCommand implements CommandExecutor {

    private final WinterCore plugin;

    public TeleportWorldCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        if (!sender.hasPermission("wintercore.teleportworld") && !sender.hasPermission("wintercore.admin")) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.WORLD-CORRECT-USAGE", "")));
            return true;
        }

        Player player = (Player) sender;
        String worldName = args[0];
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.INVALID-WORLD", "").replace("<world>", worldName)));
            return true;
        }

        player.teleport(world.getSpawnLocation());
        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORT.TELEPORTED-WORLD", "").replace("<world>", worldName)));
        return true;
    }
}