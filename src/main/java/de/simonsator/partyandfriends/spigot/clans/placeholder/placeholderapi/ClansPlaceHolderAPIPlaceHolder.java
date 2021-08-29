package de.simonsator.partyandfriends.spigot.clans.placeholder.placeholderapi;

import de.simonsator.partyandfriends.spigot.clans.placeholder.CachedClanPlaceHolder;
import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author simonbrungs
 * @version 1.0.0 10.05.17
 */
public class ClansPlaceHolderAPIPlaceHolder extends PlaceholderExpansion {
	private final String ON_EMPTY_CLAN_NAME;
	private final ClansPlaceHolder clanTagProvider;
	private final String CUSTOM_CLAN_TAG;
	private final String CUSTOM_CLAN_NAME;
	private final String ON_EMPTY;

	public ClansPlaceHolderAPIPlaceHolder(Plugin pPlugin) {
		CUSTOM_CLAN_TAG = pPlugin.getConfig().getString("PlaceholderCustomDesign.Placeholder");
		ON_EMPTY = pPlugin.getConfig().getString("PlaceholderCustomDesign.OnEmpty");

		CUSTOM_CLAN_NAME = pPlugin.getConfig().getString("PlaceholderCustomDesign.ClanName.Placeholder");
		ON_EMPTY_CLAN_NAME = pPlugin.getConfig().getString("PlaceholderCustomDesign.ClanName.OnEmpty");
		if (pPlugin.getConfig().getBoolean("UseCache")) {
			clanTagProvider = new CachedClanPlaceHolder(pPlugin.getConfig().getInt("CacheTimeInSeconds"), pPlugin);
		} else
			clanTagProvider = new ClansPlaceHolder() {
			};
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public String getAuthor() {
		return "Simonsator";
	}

	@Override
	public String getIdentifier() {
		return "clantagprovider";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String onPlaceholderRequest(Player pPlayer, String pIdentifier) {
		if (pPlayer == null)
			return "";
		switch (pIdentifier) {
			case "clantag":
				return clanTagProvider.getClanTag(pPlayer.getName());
			case "clantag_custom_design":
				String clanTag = clanTagProvider.getClanTag(pPlayer.getName());
				if (!clanTag.isEmpty())
					return CUSTOM_CLAN_TAG.replace("[%CLAN_TAG%]", clanTag);
				return ON_EMPTY;
			case "clanname":
				String clanName = clanTagProvider.getClanName(pPlayer.getName());
				if (!clanName.isEmpty())
					return CUSTOM_CLAN_NAME.replace("[%CLAN_NAME%]", clanName);
				return ON_EMPTY_CLAN_NAME;
			case "colored_clanname":
				String coloredClanName = clanTagProvider.getColoredClanName(pPlayer.getName());
				if (!coloredClanName.isEmpty())
					return CUSTOM_CLAN_NAME.replace("[%CLAN_NAME%]", coloredClanName);
				return ON_EMPTY_CLAN_NAME;
			case "clan_color":
				return clanTagProvider.getClanColor(pPlayer.getName());
			default:
				return null;
		}
	}
}
