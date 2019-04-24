package de.simonsator.partyandfriends.spigot.clans;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import de.simonsator.partyandfriends.spigot.clans.api.event.ClanTagOfPlayerChangedEvent;
import de.simonsator.partyandfriends.spigot.clans.api.event.PlayerJoinedClanEvent;
import de.simonsator.partyandfriends.spigot.clans.api.event.PlayerLeftClanEvent;
import de.simonsator.partyandfriends.spigot.clans.clans.clansmanager.MySQLClansManager;
import de.simonsator.partyandfriends.spigot.clans.placeholder.mvdw.ClansPlaceHolderCustomDesign;
import de.simonsator.partyandfriends.spigot.clans.placeholder.mvdw.ClansPlaceHolderDefault;
import de.simonsator.partyandfriends.spigot.clans.placeholder.placeholderapi.ClansPlaceHolderAPIPlaceHolder;
import de.simonsator.partyandfriends.spigot.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simonbrungs
 * @version 1.0.0 26.10.16
 */
public class ClansMainSpigot extends JavaPlugin implements Listener, PluginMessageListener {
	private ClanConnection connection;
	private static ClansMainSpigot instance;
	private String newDisplayName;
	private HashMap<UUID, String> originalDisplayNames = new HashMap<>();
	private static final String CHANNEL = "paf:gui";
	private final Gson GSON = new Gson();
	private final Pattern CLAN_PATTERN = Pattern.compile("[%CLAN_TAG%]", Pattern.LITERAL);
	private final Pattern PLAYER_DISPLAY_NAME_PATTERN = Pattern.compile("[%PLAYER_DISPLAY_NAME%]", Pattern.LITERAL);

	@Override
	public void onEnable() {
		instance = this;
		connection = new ClanConnection(Main.getInstance().getMySQLData());
		new MySQLClansManager(connection);
		getConfig().options().copyDefaults(true);
		saveConfig();
		for (String path : getConfig().getKeys(true))
			if (getConfig().isString(path))
				getConfig().set(path, ChatColor.translateAlternateColorCodes('&', getConfig().getString(path)));
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			new ClansPlaceHolderDefault(this);
			new ClansPlaceHolderCustomDesign(this);
		}
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
			new ClansPlaceHolderAPIPlaceHolder(this).hook();
		if (!getConfig().getBoolean("API-Only")) {
			newDisplayName = getConfig().getString("new-display-name");
			getServer().getPluginManager().registerEvents(this, this);
			getServer().getMessenger().registerIncomingPluginChannel(this, CHANNEL, this);
		}
	}

	public ClanConnection getConnection() {
		return connection;
	}

	public static ClansMainSpigot getInstance() {
		return instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent pEvent) {
		addClanTag(pEvent.getPlayer());
	}

	private void addClanTag(Player pPlayer) {
		originalDisplayNames.put(pPlayer.getUniqueId(), pPlayer.getDisplayName());
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pPlayer.getName());
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				pPlayer.setDisplayName(CLAN_PATTERN.matcher(PLAYER_DISPLAY_NAME_PATTERN.matcher(newDisplayName)
						.replaceAll(Matcher.quoteReplacement(pPlayer.getDisplayName()))).
						replaceAll(Matcher.quoteReplacement(clan.getClanTag())));
		}
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		if (!channel.equals(CHANNEL))
			return;
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		DataInputStream in = new DataInputStream(stream);
		try {
			executeTask(GSON.fromJson(in.readUTF(), JsonObject.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setOriginalDisplayName(Player pPlayer) {
		pPlayer.setDisplayName(originalDisplayNames.get(pPlayer.getUniqueId()));
	}

	/**
	 * Decides what should be done
	 *
	 * @param pReceived The Json which was send by the BungeeCord.
	 */
	private void executeTask(JsonObject pReceived) {
		Player player = Bukkit.getPlayer(pReceived.get("receiverName").getAsString());
		if (player == null)
			return;
		switch (pReceived.get("task").getAsString()) {
			case "InformAboutNewClan":
				Bukkit.getServer().getPluginManager().callEvent(new PlayerJoinedClanEvent(player));
				addClanTag(player);
				break;
			case "InformAboutClanLeave":
				Bukkit.getServer().getPluginManager().callEvent(new PlayerLeftClanEvent(player));
				setOriginalDisplayName(player);
				break;
			case "InformAboutClanTagChange":
				Bukkit.getServer().getPluginManager().callEvent(new ClanTagOfPlayerChangedEvent(player));
				setOriginalDisplayName(player);
				addClanTag(player);
				break;
			default:
				break;
		}
	}

}
