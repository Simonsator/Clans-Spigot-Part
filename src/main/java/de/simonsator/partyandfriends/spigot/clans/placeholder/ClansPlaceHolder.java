package de.simonsator.partyandfriends.spigot.clans.placeholder;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import net.md_5.bungee.api.ChatColor;

/**
 * @author simonbrungs
 * @version 1.0.0 10.01.17
 */
public abstract class ClansPlaceHolder {

	public String getClanTag(String pName) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pName);
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				return clan.getColoredClanTag();
		}
		return "";
	}

	public String getClanName(String pName) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pName);
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				return clan.getClanName();
		}
		return "";
	}

	public String getColoredClanName(String pName) {
		PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pName);
		if (player != null) {
			Clan clan = ClansManager.getInstance().getClan(player);
			if (clan != null)
				return clan.getColoredClanName();
		}
		return "";
	}
}
