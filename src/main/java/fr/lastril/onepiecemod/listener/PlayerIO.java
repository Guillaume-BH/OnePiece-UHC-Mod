package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerIO implements Listener {

    private final OnePieceMod plugin;

    public PlayerIO(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.plugin.isVanished(player.getUniqueId())) {
            this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
                for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
                    onlinePlayer.hidePlayer(player);
                }
            }, 2);
        }
        this.plugin.getServer().getScheduler().runTaskLater(this.plugin, () -> {
            for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
                if (this.plugin.isVanished(onlinePlayer.getUniqueId())) {
                    onlinePlayer.hidePlayer(onlinePlayer);
                }
            }
        }, 2);
    }

}
