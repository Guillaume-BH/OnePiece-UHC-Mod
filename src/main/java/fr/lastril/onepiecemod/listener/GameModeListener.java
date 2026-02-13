package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeListener implements Listener {

    private final OnePieceMod plugin;

    public GameModeListener(OnePieceMod plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    private void onGameModeChange(PlayerGameModeChangeEvent event) {
        final Player player = event.getPlayer();
        if (!player.isOp()) return;
        final GameMode mode = event.getNewGameMode();
        if (mode.equals(GameMode.SPECTATOR)) {
            this.plugin.getLunarAPI().sendModerationMods(player.getUniqueId());
        } else {
            this.plugin.getLunarAPI().removeModerationMods(player.getUniqueId());
        }
    }

}
