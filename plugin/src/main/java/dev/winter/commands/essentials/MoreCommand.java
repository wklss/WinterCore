package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MoreCommand implements CommandExecutor {

    private final WinterCore plugin;

    public MoreCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command command,@NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("wintercore.more") && !player.hasPermission("wintercore.admin")) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("MORE.CORRECT-USAGE", "")));
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("MORE.INVALID-AMOUNT", "")));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("MORE.INVALID-ITEM", "")));
            return true;
        }

        item.setAmount(amount);
        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("MORE.UPDATED", "").replace("<amount>", String.valueOf(amount))));
        return true;
    }
}