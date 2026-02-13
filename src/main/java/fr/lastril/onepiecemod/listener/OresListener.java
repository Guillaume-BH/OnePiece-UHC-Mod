package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.translate.TranslationParam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class OresListener implements Listener {

    private final OnePieceMod plugin;
    private final List<Location> oresLocationsMined;

    public OresListener(OnePieceMod plugin) {
        this.plugin = plugin;
        this.oresLocationsMined = new ArrayList<>();
    }

    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        if (block == null) return;
        if (block.getType() == Material.DIAMOND_ORE
                || block.getType() == Material.GOLD_ORE) {
            if (this.oresLocationsMined.contains(block.getLocation())) return;
            final Material type = block.getType();
            int diamondsMine = this.plugin.getDiamondsPlayer(player.getUniqueId()),
                    goldsMine = this.plugin.getGoldsPlayer(player.getUniqueId());
            final int nearbyOres = this.getNearbyOres(block, type);
            if (type == Material.DIAMOND_ORE) {
                this.plugin.setDiamondsPlayer(player.getUniqueId(), diamondsMine + 1);
                final String message = this.plugin.getMessage("alert-mining-diamond",
                        TranslationParam.from("playerName", player.getName()),
                        TranslationParam.from("number", diamondsMine + 1)
                );
                this.plugin.sendSpectatorsMessage(Component.text(message)
                        .hoverEvent(HoverEvent.showText(Component.text("§cSe téléporter")))
                        .clickEvent(ClickEvent.runCommand("/tp " + player.getName())));
                System.out.println("§f[§b!§f] §7" + player.getName() + " mined diamond (" + (diamondsMine + nearbyOres) + ") !");
            } else if (type == Material.GOLD_ORE) {
                this.plugin.setGoldsPlayer(player.getUniqueId(), goldsMine + 1);
                final String message = this.plugin.getMessage("alert-mining-gold",
                        TranslationParam.from("playerName", player.getName()),
                        TranslationParam.from("number", goldsMine + 1)
                );
                this.plugin.sendSpectatorsMessage(Component.text(message)
                        .hoverEvent(HoverEvent.showText(Component.text("§cSe téléporter")))
                        .clickEvent(ClickEvent.runCommand("/tp " + player.getName())));
                System.out.println("§f[§b!§f] §7" + player.getName() + " mined gold (" + (goldsMine + nearbyOres) + ") !");
            }
        }
    }

    private int getNearbyOres(Block block, Material oreType) {
        int ores = 0;
        for (int x = -2; x < 2; x++) {
            for (int y = -2; y < 2; y++) {
                for (int z = -2; z < 2; z++) {
                    final Block relative = block.getRelative(x, y, z);
                    if (relative.getType() == oreType) {
                        ores++;
                        this.oresLocationsMined.add(relative.getLocation());
                    }
                }
            }
        }
        return ores;
    }

}
