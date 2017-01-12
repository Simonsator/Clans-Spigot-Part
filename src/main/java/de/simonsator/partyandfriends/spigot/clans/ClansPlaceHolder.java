package de.simonsator.partyandfriends.spigot.clans;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import org.bukkit.plugin.Plugin;

/**
 * @author simonbrungs
 * @version 1.0.0 10.01.17
 */
public class ClansPlaceHolder implements PlaceholderReplacer {
	public ClansPlaceHolder(Plugin pPlugin) {
		PlaceholderAPI.registerPlaceholder(pPlugin, "clantag", this);
	}

	@Override
	public String onPlaceholderReplace(PlaceholderReplaceEvent pEvent) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getOfflinePlayer().getUniqueId());
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				return clan.getClanTag();
		}
		return "";
	}
}
