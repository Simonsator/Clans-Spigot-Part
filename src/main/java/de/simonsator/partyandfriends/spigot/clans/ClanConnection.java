package de.simonsator.partyandfriends.spigot.clans;

import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.SQLCommunication;

import java.sql.*;
import java.util.ArrayList;

public class ClanConnection extends SQLCommunication {
	private final String TABLE_PREFIX;

	public ClanConnection(MySQLData pMySQLData) {
		super(pMySQLData.DATABASE, "jdbc:mysql://" + pMySQLData.HOST + ":" + pMySQLData.PORT, pMySQLData.USERNAME,
				pMySQLData.PASSWORD, pMySQLData.USE_SSL);
		this.TABLE_PREFIX = pMySQLData.TABLE_PREFIX;
	}

	public boolean isInvited(int pPlayerID, int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement())
					.executeQuery("select clan_id from " + DATABASE + ".`" + TABLE_PREFIX + "clans_request_assignment` WHERE player_id='"
							+ pPlayerID + "' AND clan_id='" + pClanID + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	public ArrayList<Integer> getRequests(int pPlayerID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Integer> clanRequests = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select clan_id from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_request_assignment` WHERE player_id='" + pPlayerID + "'");
			while (rs.next())
				clanRequests.add(rs.getInt("clan_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return clanRequests;
	}

	public boolean hasRequests(int pPlayerID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select clan_id from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_request_assignment` WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	public ArrayList<Integer> getRequestedPlayers(int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Integer> requested = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_request_assignment` WHERE clan_id='" + pClanID + "'");
			while (rs.next())
				requested.add(rs.getInt("player_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return requested;
	}

	public String getClanNameByID(int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement())
					.executeQuery("select clan_name from " + DATABASE + ".`" + TABLE_PREFIX + "clan` WHERE id='" + pClanID + "' LIMIT 1");
			if (rs.next())
				return rs.getString("clan_name");
			else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return null;
	}

	public int getClanIDByName(String pClanName) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("select id from " + DATABASE + ".`" + TABLE_PREFIX + "clan` WHERE clan_name=? LIMIT 1");
			prepStmt.setString(1, pClanName);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, prepStmt);
		}
		return 0;
	}

	public ArrayList<Integer> getMembersOfClan(int pClanID) {
		ArrayList<Integer> members = new ArrayList<>();
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select player_id, type from " + DATABASE + ".`" + TABLE_PREFIX + "clans_assignment` WHERE clan_id='" + pClanID + "'");
			while (rs.next()) {
				if (rs.getByte("type") == 0)
					members.add(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return members;
	}

	public ArrayList<Integer> getLeadersOfClan(int pClanID) {
		ArrayList<Integer> members = new ArrayList<>();
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select player_id, type from " + DATABASE + ".`" + TABLE_PREFIX + "clans_assignment` WHERE clan_id='" + pClanID + "' AND type='1'");
			while (rs.next())
				members.add(rs.getInt("player_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return members;
	}

	public boolean isLeader(int pPlayerID, int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select type from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_assignment` WHERE clan_id='" + pClanID + "' AND player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				if (rs.getByte("type") == 1)
					return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	public boolean isInClan(int pPlayerID, int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_assignment` WHERE clan_id='" + pClanID + "' AND player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	public ArrayList<Integer> getAllPlayersOfClan(int pClanID) {
		ArrayList<Integer> members = new ArrayList<>();
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select player_id from " + DATABASE + ".`" + TABLE_PREFIX + "clans_assignment` WHERE clan_id='" + pClanID + "'");
			while (rs.next()) {
				members.add(rs.getInt("player_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return members;
	}

	public String getClanTag(int pClanID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement())
					.executeQuery("select clan_tag from " + DATABASE + ".`" + TABLE_PREFIX + "clan` WHERE id='" + pClanID + "' LIMIT 1");
			if (rs.next())
				return rs.getString("clan_tag");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return null;
	}

	public int getClanByID(int pPlayerID) {
		Connection con = getConnection();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select clan_id from " + DATABASE
					+ ".`" + TABLE_PREFIX + "clans_assignment` WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("clan_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return 0;
	}
}