package de.simonsator.partyandfriends.spigot.clans.clans.clan;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.spigot.clans.ClansMainSpigot;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.spigot.pafplayers.mysql.PAFPlayerMySQL;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 on 24.06.2016
 */
public class MySQLClan extends Clan {
	private final int CLAN_ID;

	public MySQLClan(int pClanID) {
		CLAN_ID = pClanID;
	}

	@Override
	public List<PAFPlayer> getAllPlayers() {
		return toPAFPlayerList(ClansMainSpigot.getInstance().getConnection().getAllPlayersOfClan(CLAN_ID));
	}

	@Override
	public String getClanName() {
		return ClansMainSpigot.getInstance().getConnection().getClanNameByID(CLAN_ID);
	}

	@Override
	public String getClanTag() {
		return ClansMainSpigot.getInstance().getConnection().getClanTag(CLAN_ID);
	}

	@Override
	public String getClanColor() {
		String clanColor = ClansMainSpigot.getInstance().getConnection().getClanColor(CLAN_ID);
		if (clanColor == null)
			return null;
		return "&" + clanColor;
	}


	@Override
	public String getColoredClanTag() {
		return ChatColor.translateAlternateColorCodes('&', ClansMainSpigot.getInstance().getConnection().getColoredClanTag(CLAN_ID));
	}

	@Override
	public boolean isLeader(PAFPlayer pPlayer) {
		return ClansMainSpigot.getInstance().getConnection().isLeader(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), CLAN_ID);
	}

	@Override
	public List<PAFPlayer> getLeaders() {
		return toPAFPlayerList(ClansMainSpigot.getInstance().getConnection().getLeadersOfClan(CLAN_ID));
	}

	@Override
	public List<PAFPlayer> getMembers() {
		return toPAFPlayerList(ClansMainSpigot.getInstance().getConnection().getMembersOfClan(CLAN_ID));
	}


	@Override
	public boolean contains(PAFPlayer pPlayer) {
		return ClansMainSpigot.getInstance().getConnection().isInClan(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), CLAN_ID);
	}

	@Override
	public List<PAFPlayer> getInvitedPlayers() {
		return toPAFPlayerList(ClansMainSpigot.getInstance().getConnection().getRequestedPlayers(CLAN_ID));
	}

	@Override
	public boolean isInvited(PAFPlayer pPlayer) {
		return ClansMainSpigot.getInstance().getConnection().isInvited(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID(), getClanID());
	}

	private List<PAFPlayer> toPAFPlayerList(List<Integer> list) {
		List<PAFPlayer> list2 = new ArrayList<>();
		for (int id : list)
			list2.add(((PAFPlayerManagerMySQL) PAFPlayerManager.getInstance()).getPlayer(id));
		return list2;
	}

	private int getClanID() {
		return CLAN_ID;
	}
}
