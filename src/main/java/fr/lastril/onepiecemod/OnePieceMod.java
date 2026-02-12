package fr.lastril.onepiecemod;

import fr.lastril.onepiecemod.command.TopLuckCommand;
import fr.lastril.onepiecemod.command.VanishCommand;
import fr.lastril.onepiecemod.command.XrayCommand;
import fr.lastril.onepiecemod.launcher.LunarAPI;
import fr.lastril.onepiecemod.listener.OresListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnePieceMod extends JavaPlugin {

    private final Map<UUID, Boolean> vanished = new HashMap<>();
    private final Map<UUID, Integer> golds = new HashMap<>(), diamonds = new HashMap<>();
    private LunarAPI lunarAPI;

    @Override
    public void onEnable() {
        this.registerLauncherAPI();

        this.getCommand("xray").setExecutor(new XrayCommand(this));
        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        this.getCommand("topluck").setExecutor(new TopLuckCommand(this));

        this.getServer().getPluginManager().registerEvents(new OresListener(this), this);
    }

    public void registerLauncherAPI() {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin("Apollo-Bukkit");
        if (plugin != null) {
            this.lunarAPI = new LunarAPI();
        }
    }

    @Override
    public void onDisable() {
        this.vanished.clear();
        this.golds.clear();
        this.diamonds.clear();
        this.lunarAPI = null;
    }

    public LunarAPI getLunarAPI() {
        return lunarAPI;
    }

    public boolean isVanished(UUID uuid) {
        return this.vanished.getOrDefault(uuid, false);
    }

    public void setVanished(UUID uuid, boolean vanished) {
        this.vanished.put(uuid, vanished);
    }

    public int getDiamondsPlayer(UUID uuid) {
        return this.diamonds.getOrDefault(uuid, 0);
    }

    public void setDiamondsPlayer(UUID uuid, int diamonds) {
        this.diamonds.put(uuid, diamonds);
    }

    public int getGoldsPlayer(UUID uuid) {
        return this.golds.getOrDefault(uuid, 0);
    }

    public void setGoldsPlayer(UUID uuid, int golds) {
        this.golds.put(uuid, golds);
    }
}
