package ml.voltaic.bukkit.onetilldeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ml.voltaic.bukkitapi.Logger;
import ml.voltaic.bukkit.onetilldeath.EventListener;;

public class Main extends JavaPlugin {
	private long startTime = 0;
	private static String pluginName = "Plugin :P";
	public org.bukkit.plugin.Plugin thisPlugin;

	public void onLoad() {
		startTime = System.currentTimeMillis();
		thisPlugin = Bukkit.getPluginManager().getPlugin(getName());
	}

	public void onEnable() {
		pluginName = getName();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new EventListener(), this);
		loadConfiguration();
		Logger.info(thisPlugin, getName() + " v" + getDescription().getVersion() + " enabled!");
	}

	public void onDisable() {
		Logger.info(thisPlugin, getName() + " Disabled.");
	}

	public static Main getInstance() {
		return (Main) Bukkit.getPluginManager().getPlugin(pluginName);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equals("OneTillDeath")) {
			if (sender.hasPermission("OneTillDeath.admin")) {
				reloadConfig();
				sender.sendMessage(ChatColor.YELLOW + "Configuation Reloaded for " + getName() + ".");
			} else {
				sender.sendMessage(ChatColor.RED + "You are lacking the required permission node!");
			}
		}
		return true;
	}

	public long getStartTime() {
		return startTime;
	}

	public void loadConfiguration() {
		getConfig().getDefaults();
		saveDefaultConfig();
		Logger.info(thisPlugin, "Configuation Loaded");
	}
}
