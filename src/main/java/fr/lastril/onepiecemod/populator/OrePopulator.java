package fr.lastril.onepiecemod.populator;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;
import java.util.function.Supplier;

public final class OrePopulator extends BlockPopulator {

    private final Supplier<Integer> chanceSupplier;
    private final int maxY;
    private final Material material;

    public OrePopulator(Supplier<Integer> chanceSupplier, int maxY, Material material) {
        this.chanceSupplier = chanceSupplier;
        this.maxY = maxY;
        this.material = material;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        // Check if this chunk will contain ore
        for (int time = 0; time < 2; time++) {
            if (random.nextInt(100) < this.chanceSupplier.get()) {
                // Define the size of the ore veins
                int veinSize = random.nextInt(8) + 2; // Random value between 2 and 10

                // Choose a random location within the chunk to start the vein
                int x = random.nextInt(16) + chunk.getX() * 16;
                int y = random.nextInt(this.maxY); // Random value between 0 and maxY
                int z = random.nextInt(16) + chunk.getZ() * 16;

                // Generate the ore vein
                for (int i = 0; i < veinSize; i++) {
                    // Choose a random nearby block to replace with ore
                    int deltaX = random.nextInt(3) - 1;
                    int deltaY = random.nextInt(3) - 1;
                    int deltaZ = random.nextInt(3) - 1;
                    Block block = world.getBlockAt(x + deltaX, y + deltaY, z + deltaZ);

                    // Replace the block if it's a stone block
                    if (block.getType() == Material.STONE) {
                        block.setType(this.material);
                    }
                }
            }
        }
    }
}

