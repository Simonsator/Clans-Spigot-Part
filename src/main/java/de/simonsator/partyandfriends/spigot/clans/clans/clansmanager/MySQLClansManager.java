package de.simonsator.partyandfriends.spigot.clans.clans.clansmanager;


import de.simonsator.partyandfriends.spigot.clans.ClansMainSpigot;
import de.simonsator.partyandfriends.spigot.clans.api.Clan;
import de.simonsator.partyandfriends.spigot.clans.api.ClanPlayer;
import de.simonsator.partyandfriends.spigot.clans.api.ClansManager;
import de.simonsator.partyandfriends.spigot.clans.clans.clan.MySQLClan;
import de.simonsator.partyandfriends.spigot.clans.clans.clanplayers.MySQLClanPlayer;
import de.simonsator.partyandfriends.spigot.clans.ClanConnection;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.pafplayers.mysql.PAFPlayerMySQL;

/**
 * @author Simonsator
 * @version 1.0.0 on 31.07.16.
 */
public class MySQLClansManager extends ClansManager {
	private final ClanConnection con;

	public MySQLClansManager(ClanConnection pConnection) {
		super();
		con = pConnection;
	}

	@Override
	public Clan getClan(PAFPlayer pPlayer) {
		int clanID = con.getClanByID(((PAFPlayerMySQL) pPlayer.getPAFPlayer()).getPlayerID());
		if (clanID == 0)
			return null;
		return new MySQLClan(clanID);
	}

	@Override
	public Clan getClan(String pName) {
		return getClan(ClansMainSpigot.getInstance().getConnection().getClanIDByName(pName));
	}

	@Override
	public ClanPlayer getClanPlayer(PAFPlayer pPlayer) {
		return new MySQLClanPlayer(pPlayer);
	}

	public Clan getClan(int pClanID) {
		if (pClanID != 0)
			return new MySQLClan(pClanID);
		return null;
	}
}
