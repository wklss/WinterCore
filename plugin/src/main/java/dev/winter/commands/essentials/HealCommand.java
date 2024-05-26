package dev.winter.commands.essentials;

import dev.winter.WinterCore;
import dev.winter.utils.Parse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealCommand implements CommandExecutor {

    private final WinterCore plugin;

    public HealCommand(WinterCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("wintercore.heal") && !sender.hasPermission("wintercore.admin")) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.NO-PERMISSIONS", "")));
            return true;
        }

        Player target = null;

        if (args.length > 0 && sender.hasPermission("wintercore.heal.others")) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.PLAYER-NOT-FOUND", "")));
                return true;
            }
        } else if (!(sender instanceof Player)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("UTIL.CONSOLE-COMMAND", "")));
            return true;
        } else {
            target = (Player) sender;
        }

        for (PotionEffect effect : target.getActivePotionEffects()) {
            target.removePotionEffect(effect.getType());
        }

        target.setHealth(20);
        target.setFoodLevel(20);
        target.setSaturation(20);

        target.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));

        if (sender.equals(target)) {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("HEAL.UPDATED", "")));
        } else {
            sender.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("HEAL.UPDATED-OTHER", "").replace("<player>", target.getName())));
            target.sendMessage(Parse.parse(plugin.getManager().getMessages().getString("HEAL.UPDATED", "")));
        }

        return true;
    }
}