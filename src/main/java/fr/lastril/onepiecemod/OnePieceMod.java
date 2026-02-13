package fr.lastril.onepiecemod;

import fr.lastril.onepiecemod.command.AlertCommand;
import fr.lastril.onepiecemod.command.TopLuckCommand;
import fr.lastril.onepiecemod.command.VanishCommand;
import fr.lastril.onepiecemod.launcher.LunarAPI;
import fr.lastril.onepiecemod.listener.EntityDamages;
import fr.lastril.onepiecemod.listener.GameModeListener;
import fr.lastril.onepiecemod.listener.OresListener;
import fr.lastril.onepiecemod.listener.WorldInitialization;
import fr.lastril.onepiecemod.translate.TranslationParam;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class OnePieceMod extends JavaPlugin {

    public static final Random RANDOM = new Random();
    private final Map<UUID, Boolean> vanished = new HashMap<>();
    private final Map<UUID, Integer> golds = new HashMap<>(), diamonds = new HashMap<>();
    private final Map<UUID, Boolean> alerts = new HashMap<>();
    private LunarAPI lunarAPI;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.adventure = BukkitAudiences.create(this);
        this.registerLauncherAPI();

        this.getCommand("vanish").setExecutor(new VanishCommand(this));
        this.getCommand("topluck").setExecutor(new TopLuckCommand(this));
        this.getCommand("alert").setExecutor(new AlertCommand(this));

        this.getServer().getPluginManager().registerEvents(new OresListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldInitialization(this), this);
        this.getServer().getPluginManager().registerEvents(new EntityDamages(this), this);
        this.getServer().getPluginManager().registerEvents(new GameModeListener(this), this);
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
        if (this.adventure != null) this.adventure.close();
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

    public boolean haveAlert(UUID uuid){
        return this.alerts.getOrDefault(uuid, false);
    }

    public void setAlerts(UUID uuid, boolean alerts) {
        this.alerts.put(uuid, alerts);
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }

    public int getBoostPercentage(String key) {
        return this.getConfig().getInt("boost." + key);
    }

    public String getMessage(String key, TranslationParam... params) {
        String message = this.getConfig().getString("message." + key);
        for (final TranslationParam param : params) {
            final String paramKey = param.getParamKey();
            final Object value = param.getValue();
            message = message.replace("{" + paramKey + "}", value.toString());
        }
        return message;
    }

    public void sendSpectatorsMessage(Component component) {
        for (Player onlinePlayer : this.getServer().getOnlinePlayers()) {
            if (!onlinePlayer.getGameMode().equals(GameMode.SPECTATOR)) continue;
            this.adventure.player(onlinePlayer).sendMessage(component);
        }
    }
}
