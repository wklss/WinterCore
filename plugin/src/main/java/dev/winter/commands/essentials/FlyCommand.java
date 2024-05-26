package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    private final WinterCore plugin;

    public FlyCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length > 1) {
            return false;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("wintercore.fly") && !player.hasPermission("wintercore.admin")) {
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
                return true;
            }

            toggleFly(player);
            return true;
        } else {
            if (!sender.hasPermission("wintercore.fly.others") && !sender.hasPermission("wintercore.admin")) {
                sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.PLAYER-NOT-FOUND", "")));
                return true;
            }

            toggleFly(targetPlayer);
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("FLY.UPDATED-OTHER", "").replace("<player>", targetPlayer.getName())));
            return true;
        }
    }

    private void toggleFly(Player player) {
        boolean flyMode = !player.getAllowFlight();
        player.setAllowFlight(flyMode);
        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("FLY.UPDATED", "").replace("<mode>", flyMode ? "enabled" : "disabled")));
    }
}
