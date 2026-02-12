package fr.lastril.onepiecemod.command;

import fr.lastril.onepiecemod.OnePieceMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    private final OnePieceMod plugin;

    public VanishCommand(OnePieceMod plugin) {
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
            player.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            return false;
        }
        if (this.plugin.isVanished(player.getUniqueId())) {
            this.plugin.setVanished(player.getUniqueId(), false);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
            player.sendMessage("§3§lMOD§r §7┃§r Vous êtes maintenant§a invisible§f aux yeux des autres joueurs.");
        } else {
            this.plugin.setVanished(player.getUniqueId(), true);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(player);
            }
            player.sendMessage("§3§lMOD§r §7┃§r Vous êtes maintenant§c visible§f aux yeux des autres joueurs.");
        }
        return true;
    }
}
