package de.simonsator.partyandfriends.spigot.clans.api;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;

/**
 * @author Simonsator
 * @version 1.0.0 on 24.06.2016
 */
public abstract class ClansManager {
	private static ClansManager instance;

	protected ClansManager() {
		instance = this;
	}

	public static ClansManager getInstance() {
		return instance;
	}

	public abstract Clan getClan(PAFPlayer pPlayer);

	public abstract Clan getClan(String pName);

	public abstract ClanPlayer getClanPlayer(PAFPlayer pPlayer);
}
