package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.translate.TranslationParam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;

public class OresListener implements Listener {

    private static final long ALERT_DELAY = 15 * 1000L;
    private final Map<UUID, Long> lastMiningDiamonds = new HashMap<>();
    private final Map<UUID, Long> lastMiningGolds = new HashMap<>();

    private final OnePieceMod plugin;

    public OresListener(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    private void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        if (block == null) return;
        if (block.getType() == Material.DIAMOND_ORE
                || block.getType() == Material.GOLD_ORE) {
            final Material type = block.getType();
            int diamondsMine = this.plugin.getDiamondsPlayer(player.getUniqueId()),
                    goldsMine = this.plugin.getGoldsPlayer(player.getUniqueId());
            final long timestamp = System.currentTimeMillis();
            if (type == Material.DIAMOND_ORE) {
                final long playerMine = this.lastMiningDiamonds.getOrDefault(player.getUniqueId(), 0L);
                this.plugin.setDiamondsPlayer(player.getUniqueId(), diamondsMine + 1);
                if (timestamp - playerMine >= ALERT_DELAY) {
                    this.lastMiningDiamonds.put(player.getUniqueId(), timestamp);
                    final String message = this.plugin.getMessage("alert-mining-diamond",
                            TranslationParam.from("playerName", player.getName()),
                            TranslationParam.from("number", diamondsMine + 1)
                    );
                    this.plugin.sendSpectatorsMessage(Component.text(message)
                            .hoverEvent(HoverEvent.showText(Component.text("§cSe téléporter")))
                            .clickEvent(ClickEvent.runCommand("/tp " + player.getName())));
                    System.out.println("§f[§b!§f] §7" + player.getName() + " mined diamond (" + (diamondsMine) + ") !");
                }
            }
            if (type == Material.GOLD_ORE) {
                this.plugin.setGoldsPlayer(player.getUniqueId(), goldsMine + 1);
                final long playerMine = this.lastMiningGolds.getOrDefault(player.getUniqueId(), 0L);
                if (timestamp - playerMine >= ALERT_DELAY) {
                    this.lastMiningGolds.put(player.getUniqueId(), timestamp);
                    final String message = this.plugin.getMessage("alert-mining-gold",
                            TranslationParam.from("playerName", player.getName()),
                            TranslationParam.from("number", goldsMine + 1)
                    );
                    this.plugin.sendSpectatorsMessage(Component.text(message)
                            .hoverEvent(HoverEvent.showText(Component.text("§cSe téléporter")))
                            .clickEvent(ClickEvent.runCommand("/tp " + player.getName())));
                    System.out.println("§f[§b!§f] §7" + player.getName() + " mined gold (" + (goldsMine) + ") !");
                }
            }
        }
    }

}
