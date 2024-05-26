package dev.winter;

import dev.winter.commands.essentials.*;
import dev.winter.commands.teleportation.*;
import dev.winter.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WinterCore extends JavaPlugin {
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // ConfigManager startup logic
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        // Register commands
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("rename").setExecutor(new RenameCommand(this));
        getCommand("more").setExecutor(new MoreCommand(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("teleport").setExecutor(new TeleportCommand(this));
        getCommand("teleporthere").setExecutor(new TeleportHereCommand(this));
        getCommand("teleportworld").setExecutor(new TeleportWorldCommand(this));
        getCommand("teleportposition").setExecutor(new TeleportPositionCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("lore").setExecutor(new LoreCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        configManager.saveConfig();
    }

    public ConfigManager getManager() {
        return configManager;
    }
}
