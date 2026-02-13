package fr.lastril.onepiecemod.populator;

import fr.lastril.onepiecemod.OnePieceMod;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.WorldGenCaves;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public final class MoreCavesPopulator extends BlockPopulator {

    private final int boostPercentage;

    public MoreCavesPopulator(int boostPercentage) {
        this.boostPercentage = boostPercentage;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if (chunk == null) {
            return;
        }
        // Gen more caves
        CraftWorld handle = (CraftWorld) world;
        for (int i = 0; i < 3; i++) {
            int xr = OnePieceMod.RANDOM.nextInt(100);
            if (xr <= boostPercentage) {
                new WorldGenCaves().a(handle.getHandle().chunkProviderServer, handle.getHandle(), chunk.getX(),
                        chunk.getZ(), new ChunkSnapshot());

            }
        }
    }
}
