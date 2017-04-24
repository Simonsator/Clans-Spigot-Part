package de.simonsator.partyandfriends.spigot.clans.placeholder.mvdw;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author simonbrungs
 * @version 1.0.0 23.01.17
 */
public class ClansPlaceHolderCustomDesign extends ClansPlaceHolder implements PlaceholderReplacer {
	private final Matcher CLAN_MATCHER;

	public ClansPlaceHolderCustomDesign(Plugin pPlugin) {
		PlaceholderAPI.registerPlaceholder(pPlugin, "clantag_custom_design", this);
		CLAN_MATCHER = Pattern.compile("[%CLAN_TAG%]", Pattern.LITERAL).matcher(pPlugin.getConfig().getString("PlaceholderCustomDesign"));
	}

	@Override
	public String onPlaceholderReplace(PlaceholderReplaceEvent pEvent) {
		String clanTag = getClanTag(pEvent.getOfflinePlayer().getUniqueId());
		if (!clanTag.isEmpty())
			return CLAN_MATCHER.replaceFirst(clanTag);
		return clanTag;
	}

}
