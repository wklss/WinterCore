package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class RenameCommand implements CommandExecutor {

    private final WinterCore plugin;

    public RenameCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("wintercore.rename") && !player.hasPermission("wintercore.admin")) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("RENAME.CORRECT-USAGE", "")));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("RENAME.INVALID-ITEM", "")));
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("RENAME.FAILED-TO-GET-ITEM-META", "")));
            return true;
        }

        String newName = String.join(" ", args);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', newName));
        item.setItemMeta(meta);

        player.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("RENAME.UPDATED", "").replace("<newname>", newName)));
        return true;
    }
}