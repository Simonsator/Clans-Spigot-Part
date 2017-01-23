package de.simonsator.partyandfriends.placeholder;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;

import java.util.UUID;

/**
 * @author simonbrungs
 * @version 1.0.0 10.01.17
 */
public abstract class ClansPlaceHolder {

	public String getClanTag(UUID pUUID) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pUUID);
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				return clan.getClanTag();
		}
		return "";
	}
}
