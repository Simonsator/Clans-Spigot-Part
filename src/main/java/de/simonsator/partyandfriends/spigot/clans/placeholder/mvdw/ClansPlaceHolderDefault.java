package de.simonsator.partyandfriends.spigot.clans.placeholder.mvdw;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import de.simonsator.partyandfriends.spigot.clans.placeholder.ClansPlaceHolder;
import org.bukkit.plugin.Plugin;

/**
 * @author simonbrungs
 * @version 1.0.0 23.01.17
 */
public class ClansPlaceHolderDefault extends ClansPlaceHolder implements PlaceholderReplacer {
	public ClansPlaceHolderDefault(Plugin pPlugin) {
		PlaceholderAPI.registerPlaceholder(pPlugin, "clantag", this);
	}

	@Override
	public String onPlaceholderReplace(PlaceholderReplaceEvent pEvent) {
		return getClanTag(pEvent.getOfflinePlayer().getName());
	}

}
