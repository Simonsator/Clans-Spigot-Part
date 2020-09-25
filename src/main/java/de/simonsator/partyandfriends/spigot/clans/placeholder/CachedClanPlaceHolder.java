package de.simonsator.partyandfriends.spigot.clans.placeholder;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CachedClanPlaceHolder extends ClansPlaceHolder {
	private Map<String, String> clanTagCache = new HashMap<>();
	private Map<String, String> clanNameCache = new HashMap<>();

	public CachedClanPlaceHolder(int cacheTimeInSeconds, Plugin pPlugin) {
		pPlugin.getServer().getScheduler().runTaskTimerAsynchronously(pPlugin, () -> {
			clanNameCache = new HashMap<>();
			clanTagCache = new HashMap<>();
		}, cacheTimeInSeconds * 20, cacheTimeInSeconds * 20);
	}

	public String getClanTag(String pName) {
		String tag = clanTagCache.get(pName);
		if (tag != null)
			return tag;
		clanTagCache.put(pName, tag = super.getClanTag(pName));
		return tag;
	}

	public String getClanName(String pName) {
		String name = clanNameCache.get(pName);
		if (name != null)
			return name;
		clanNameCache.put(pName, name = super.getClanName(pName));
		return name;
	}

}
