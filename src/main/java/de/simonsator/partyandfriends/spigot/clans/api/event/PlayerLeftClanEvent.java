package de.simonsator.partyandfriends.spigot.clans.api.event;

import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLeftClanEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private final Player PLAYER;

	public PlayerLeftClanEvent(Player pPlayer) {
		PLAYER = pPlayer;
	}

	public PAFPlayer getPAFPlayer() {
		return PAFPlayerManager.getInstance().getPlayer(PLAYER.getName());
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}
