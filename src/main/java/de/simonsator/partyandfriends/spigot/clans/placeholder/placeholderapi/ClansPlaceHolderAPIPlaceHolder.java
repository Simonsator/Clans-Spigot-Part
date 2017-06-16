package de.simonsator.partyandfriends.spigot.clans.placeholder.placeholderapi;

import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simonbrungs
 * @version 1.0.0 10.05.17
 */
public class ClansPlaceHolderAPIPlaceHolder extends EZPlaceholderHook {
	private ClansPlaceHolder clanTagProvider = new ClansPlaceHolder() {
	};
	private final Matcher CLAN_MATCHER;

	public ClansPlaceHolderAPIPlaceHolder(Plugin pPlugin) {
		super(pPlugin, "clantagprovider");
		hook();
		CLAN_MATCHER = Pattern.compile("[%CLAN_TAG%]", Pattern.LITERAL).matcher(pPlugin.getConfig().getString("PlaceholderCustomDesign"));
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
					return CLAN_MATCHER.replaceFirst(clanTag);
				return clanTag;
			default:
				return null;
		}
	}
}
