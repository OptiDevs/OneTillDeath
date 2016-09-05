package ml.voltaic.bukkit.onetilldeath;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import ml.voltaic.bukkit.onetilldeath.Main;
import net.md_5.bungee.api.ChatColor;

public class EventListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage().replaceAll(" ", "_");
		for (String s : Main.getInstance().getConfig().getStringList("commandList")) {
			if (s.startsWith("/"))
				s = s.replaceFirst("/", "");
			if (message.toLowerCase().startsWith(s.toLowerCase(), 1)) {
				if (!p.hasPermission("OneTillDeath.bypass." + s)) {
					if (!Main.getInstance().getConfig().contains("UserData." + p.getUniqueId()))
						Main.getInstance().getConfig().createSection("UserData." + p.getUniqueId());

					if (!Main.getInstance().getConfig().contains("UserData." + p.getUniqueId() + "." + s)) {
						Main.getInstance().getConfig().createSection("UserData." + p.getUniqueId() + "." + s);
						Main.getInstance().getConfig().set("UserData." + p.getUniqueId() + "." + s, true);
						Main.getInstance().saveConfig();
						return;
					}

					if (Main.getInstance().getConfig().getBoolean("UserData." + p.getUniqueId() + "." + s)) {
						p.sendMessage(ChatColor.RED + "You must first die in order to use this command agian!");
						e.setCancelled(true);
						Main.getInstance().saveConfig();
					} else {
						Main.getInstance().getConfig().set("UserData." + p.getUniqueId() + "." + s, true);
						Main.getInstance().saveConfig();
					}

				}
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		for (String s : Main.getInstance().getConfig().getStringList("commandList")) {
			if (s.startsWith("/"))
				s = s.replaceFirst("/", "");

			if (!Main.getInstance().getConfig().contains("UserData." + p.getUniqueId()))
				Main.getInstance().getConfig().createSection("UserData." + p.getUniqueId());

			if (!Main.getInstance().getConfig().contains("UserData." + p.getUniqueId() + "." + s)) {
				Main.getInstance().getConfig().createSection("UserData." + p.getUniqueId() + "." + s);
				Main.getInstance().getConfig().set("UserData." + p.getUniqueId() + "." + s, false);
			} else {
				p.sendMessage(ChatColor.GREEN + "You may now use the '/" + s + "' command!");
				Main.getInstance().getConfig().set("UserData." + p.getUniqueId() + "." + s, false);
			}
		}
		Main.getInstance().saveConfig();
	}
}
