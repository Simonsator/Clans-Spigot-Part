package de.simonsator.partyandfriends.spigot.clans;

import de.simonsator.partyandfriends.spigot.clans.clans.clansmanager.MySQLClansManager;
import de.simonsator.partyandfriends.clan.ClanConnection;
import de.simonsator.partyandfriends.spigot.main.Main;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author simonbrungs
 * @version 1.0.0 26.10.16
 */
public class ClansMainSpigot extends JavaPlugin {
	private ClanConnection connection;
	private static ClansMainSpigot instance;

	@Override
	public void onEnable() {
		instance = this;
		connection = new ClanConnection(Main.getInstance().getMySQLData());
		new MySQLClansManager(connection);
	}

	public ClanConnection getConnection() {
		return connection;
	}

	public static ClansMainSpigot getInstance() {
		return instance;
	}
}
