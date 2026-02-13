package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.populator.MoreCavesPopulator;
import fr.lastril.onepiecemod.populator.OrePopulator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public final class WorldInitialization implements Listener {

    private final OnePieceMod plugin;

    public WorldInitialization(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onWorldInit(WorldInitEvent event) {
        final World world = event.getWorld();
        if (!world.getEnvironment().equals(World.Environment.NORMAL)) return;
        world.getPopulators().add(new MoreCavesPopulator(this.plugin.getBoostPercentage("caves")));
        world.getPopulators().add(new OrePopulator(() -> this.plugin.getBoostPercentage("gold"), 34, Material.GOLD_ORE));
        world.getPopulators().add(new OrePopulator(() -> this.plugin.getBoostPercentage("diamond"),  15, Material.DIAMOND_ORE));
    }
}
