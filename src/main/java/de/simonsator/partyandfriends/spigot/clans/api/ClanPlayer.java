package de.simonsator.partyandfriends.spigot.clans.api;


import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;

import java.util.List;

/**
 * @author 00pfl
 * @version 1.0.0 on 25.06.2016
 */
public abstract class ClanPlayer {
	private final PAFPlayer PLAYER;

	public ClanPlayer(PAFPlayer pPlayer) {
		PLAYER = pPlayer;
	}

	public abstract List<Clan> getClanRequests();

	public PAFPlayer getPlayer() {
		return PLAYER;
	}

	public abstract boolean hasInvitations();
}
