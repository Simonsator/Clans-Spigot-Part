package de.simonsator.partyandfriends.spigot.clans.placeholder.placeholderapi;

import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Pattern;

/**
 * @author simonbrungs
 * @version 1.0.0 10.05.17
 */
public class ClansPlaceHolderAPIPlaceHolder extends EZPlaceholderHook {
	private ClansPlaceHolder clanTagProvider = new ClansPlaceHolder() {
	};
	private final String CUSTOM_CLAN_TAG;
	private final String ON_EMPTY;

	public ClansPlaceHolderAPIPlaceHolder(Plugin pPlugin) {
		super(pPlugin, "clantagprovider");
		hook();
		CUSTOM_CLAN_TAG = pPlugin.getConfig().getString("PlaceholderCustomDesign.Placeholder");
		ON_EMPTY = pPlugin.getConfig().getString("PlaceholderCustomDesign.OnEmpty");
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
			default:
				return null;
		}
	}
}
