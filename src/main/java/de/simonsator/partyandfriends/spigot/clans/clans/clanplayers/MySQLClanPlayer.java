package de.simonsator.partyandfriends.spigot.clans.clans.clanplayers;

import de.simonsator.partyandfriends.spigot.clans.ClansMainSpigot;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClanPlayer;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import de.simonsator.partyandfriends.spigot.clans.clans.clansmanager.MySQLClansManager;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.pafplayers.mysql.PAFPlayerMySQL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simonsator
 * @version 1.0.0 on 31.07.16.
 */
public class MySQLClanPlayer extends ClanPlayer {
	public MySQLClanPlayer(PAFPlayer pPlayer) {
		super(pPlayer);
	}

	@Override
	public List<Clan> getClanRequests() {
		return toClans(ClansMainSpigot.getInstance().getConnection().getRequests(getPlayerID()));
	}

	private int getPlayerID() {
		return ((PAFPlayerMySQL) getPlayer()).getPlayerID();
	}

	@Override
	public boolean hasInvitations() {
		return ClansMainSpigot.getInstance().getConnection().hasRequests(getPlayerID());
	}


	private List<Clan> toClans(ArrayList<Integer> toConvert) {
		List<Clan> list = new ArrayList<>();
		for (int id : toConvert)
			list.add(((MySQLClansManager) ClansManager.getInstance()).getClan(id));
		return list;
	}

}
