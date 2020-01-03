package de.simonsator.partyandfriends.spigot.clans.api;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;

import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 on 24.06.2016
 */
public abstract class Clan {
	public abstract List<PAFPlayer> getAllPlayers();

	public abstract String getClanName();

	public abstract String getClanTag();

	public abstract String getClanColor();

	public abstract String getColoredClanTag();

	public abstract boolean isLeader(PAFPlayer pPlayer);

	public abstract List<PAFPlayer> getLeaders();

	public abstract List<PAFPlayer> getMembers();

	public abstract boolean contains(PAFPlayer pPlayer);

	public abstract List<PAFPlayer> getInvitedPlayers();

	public abstract boolean isInvited(PAFPlayer pPlayer);
}
