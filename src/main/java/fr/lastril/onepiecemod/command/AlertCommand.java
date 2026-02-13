package fr.lastril.onepiecemod.command;

import fr.lastril.onepiecemod.OnePieceMod;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertCommand implements CommandExecutor {

    private final OnePieceMod plugin;

    public AlertCommand(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cVous devez être un joueur pour effectuer cette commande.");
            return false;
        }
        final Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage(this.plugin.getMessage("no-permission"));
            return false;
        }
        if (this.plugin.haveAlert(player.getUniqueId())) {
            this.plugin.setAlerts(player.getUniqueId(), false);
            player.sendMessage(this.plugin.getMessage("alert-off"));
        } else {
            this.plugin.setAlerts(player.getUniqueId(), true);
            player.sendMessage(this.plugin.getMessage("alert-on"));
        }
        return true;
    }
}
