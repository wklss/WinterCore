package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand implements CommandExecutor {

    private final WinterCore plugin;

    public LoreCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("wintercore.lore") && !player.hasPermission("wintercore.admin")) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.CORRECT-USAGE", "")));
            return true;
        }

        String subCommand = args[0];
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.INVALID-ITEM", "")));
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.FAILED-TO-GET-ITEM-META", "")));
            return true;
        }

        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        if (subCommand.equalsIgnoreCase("add")) {
            StringBuilder loreText = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                loreText.append(args[i]).append(" ");
            }
            String formattedLore = ChatColor.translateAlternateColorCodes('&', loreText.toString().trim());
            lore.add(formattedLore);
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.ADDED", "").replace("<text>", formattedLore)));
        } else if (subCommand.equalsIgnoreCase("remove")) {
            int lineNumber;
            try {
                lineNumber = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.INVALID-LINE", "")));
                return true;
            }
            if (lineNumber >= 0 && lineNumber < lore.size()) {
                lore.remove(lineNumber);
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.REMOVED", "").replace("<line>", String.valueOf(lineNumber + 1))));
            } else {
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.INVALID-LINE", "")));
            }
        } else if (subCommand.equalsIgnoreCase("set")) {
            int lineNumber;
            try {
                lineNumber = Integer.parseInt(args[1]) - 1;
            } catch (NumberFormatException e) {
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.INVALID-LINE", "")));
                return true;
            }
            StringBuilder loreText = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                loreText.append(args[i]).append(" ");
            }
            String formattedLore = ChatColor.translateAlternateColorCodes('&', loreText.toString().trim());
            if (lineNumber >= 0 && lineNumber < lore.size()) {
                lore.set(lineNumber, formattedLore);
                meta.setLore(lore);
                item.setItemMeta(meta);
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.SET", "").replace("<line>", String.valueOf(lineNumber + 1)).replace("<text>", formattedLore)));
            } else {
                player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.INVALID-LINE", "")));
            }
        } else {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("LORE.CORRECT-USAGE", "")));
        }

        return true;
    }
}