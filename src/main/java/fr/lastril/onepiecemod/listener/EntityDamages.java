package fr.lastril.onepiecemod.listener;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.translate.TranslationParam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityDamages implements Listener {

    private static final long ALERT_DELAY = 15 * 1000L;

    private final OnePieceMod plugin;
    private final Map<UUID, Long> lastDamages = new HashMap<>();

    public EntityDamages(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerAttackPlayer(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity() instanceof Player) {
            final Player player = (Player) event.getEntity();
            if (event.getDamager() instanceof Arrow) {
                final Arrow arrow = (Arrow) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    final Player shooter = (Player) arrow.getShooter();
                    sendAlert(shooter, player.getName());
                }
            }
            if (event.getDamager() instanceof Player) {
                final Player attacker = (Player) event.getDamager();
                sendAlert(attacker, player.getName());
            }
        }
    }

    private void sendAlert(Player attacker, String name) {
        final long timestamp = System.currentTimeMillis();
        final long attackerFight = this.lastDamages.getOrDefault(attacker.getUniqueId(), 0L);
        if (timestamp - attackerFight >= ALERT_DELAY) {
            this.lastDamages.put(attacker.getUniqueId(), timestamp);
            final String message = this.plugin.getMessage("alert-fight",
                    TranslationParam.from("attacker", attacker.getName()),
                    TranslationParam.from("attacked", name)
            );
            this.plugin.sendSpectatorsMessage(Component.text(message)
                    .hoverEvent(HoverEvent.showText(Component.text("§cSe téléporter")))
                    .clickEvent(ClickEvent.runCommand("/tp " + attacker.getName())));
        }
    }
}
