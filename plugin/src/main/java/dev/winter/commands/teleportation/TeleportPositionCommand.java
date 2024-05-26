package dev.winter.commands.teleportation;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportPositionCommand implements CommandExecutor {

    private final WinterCore plugin;

    public TeleportPositionCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        if (!sender.hasPermission("wintercore.teleportposition") && !sender.hasPermission("wintercore.admin")) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORTPOSITION.CORRECT-USAGE", "")));
            return true;
        }

        Player player = (Player) sender;
        double x, y, z;

        try {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORTPOSITION.INVALID-COORDINATES", "")));
            return true;
        }

        Location location = new Location(player.getWorld(), x, y, z);
        player.teleport(location);
        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("TELEPORTPOSITION.TELEPORTED", "").replace("<x>", String.valueOf(x)).replace("<y>", String.valueOf(y)).replace("<z>", String.valueOf(z))));
        return true;
    }
}