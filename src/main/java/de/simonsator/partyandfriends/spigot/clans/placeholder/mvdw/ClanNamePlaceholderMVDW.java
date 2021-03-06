package de.simonsator.partyandfriends.spigot.clans.placeholder.mvdw;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import org.bukkit.plugin.Plugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClanNamePlaceholderMVDW extends ClansPlaceHolder implements PlaceholderReplacer {
	private final Matcher CLAN_MATCHER;
	private final String ON_EMPTY;

	public ClanNamePlaceholderMVDW(Plugin pPlugin) {
		PlaceholderAPI.registerPlaceholder(pPlugin, "clanname", this);
		CLAN_MATCHER = Pattern.compile("[%CLAN_NAME%]", Pattern.LITERAL).matcher(pPlugin.getConfig().getString("PlaceholderCustomDesign.ClanName.Placeholder"));
		ON_EMPTY = pPlugin.getConfig().getString("PlaceholderCustomDesign.ClanName.OnEmpty");
	}

	@Override
	public String onPlaceholderReplace(PlaceholderReplaceEvent pEvent) {
		String clanName = getClanName(pEvent.getOfflinePlayer().getName());
		if (!clanName.isEmpty())
			return CLAN_MATCHER.replaceFirst(clanName);
		return ON_EMPTY;
	}

}
