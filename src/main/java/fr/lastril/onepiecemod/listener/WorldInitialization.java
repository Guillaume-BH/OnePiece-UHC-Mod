package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.populator.OrePopulator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public final class WorldInitialization implements Listener {

    private final OnePieceMod plugin;

    public WorldInitialization(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onChunkLoad(ChunkLoadEvent event) {
        if (!event.isNewChunk()) return;

        World world = event.getWorld();
        if (world.getEnvironment() != World.Environment.NORMAL) return;

        new OrePopulator(() -> this.plugin.getBoostPercentage("gold"), 34, Material.GOLD_ORE)
                .populate(world, OnePieceMod.RANDOM, event.getChunk());
        new OrePopulator(() -> this.plugin.getBoostPercentage("diamond"), 15, Material.DIAMOND_ORE)
                .populate(world, OnePieceMod.RANDOM, event.getChunk());
    }
}
