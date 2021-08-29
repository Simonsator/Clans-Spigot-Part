package de.simonsator.partyandfriends.spigot.clans.placeholder;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class CachedClanPlaceHolder extends ClansPlaceHolder {
	private Map<String, String> clanTagCache = new HashMap<>();
	private Map<String, String> clanNameCache = new HashMap<>();
	private Map<String, String> clanColorCache = new HashMap<>();
	private Map<String, String> coloredClanNameCache = new HashMap<>();

	public CachedClanPlaceHolder(int cacheTimeInSeconds, Plugin pPlugin) {
		pPlugin.getServer().getScheduler().runTaskTimerAsynchronously(pPlugin, () -> {
			clanNameCache = new HashMap<>();
			coloredClanNameCache = new HashMap<>();
			clanTagCache = new HashMap<>();
			clanColorCache = new HashMap<>();
		}, cacheTimeInSeconds * 20L, cacheTimeInSeconds * 20L);
	}

	@Override
	public String getClanTag(String pName) {
		String tag = clanTagCache.get(pName);
		if (tag != null)
			return tag;
		clanTagCache.put(pName, tag = super.getClanTag(pName));
		return tag;
	}

	@Override
	public String getColoredClanName(String pName) {
		String name = coloredClanNameCache.get(pName);
		if (name != null)
			return name;
		coloredClanNameCache.put(pName, name = super.getColoredClanName(pName));
		return name;
	}

	@Override
	public String getClanName(String pName) {
		String name = clanNameCache.get(pName);
		if (name != null)
			return name;
		clanNameCache.put(pName, name = super.getClanName(pName));
		return name;
	}

	@Override
	public String getClanColor(String pName) {
		String name = clanColorCache.get(pName);
		if (name != null)
			return name;
		clanColorCache.put(pName, name = super.getClanColor(pName));
		return name;
	}

}
