package fr.lastril.onepiecemod.command;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.launcher.LunarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XrayCommand implements CommandExecutor {

    private final OnePieceMod plugin;

    public XrayCommand(OnePieceMod plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Vous devez être un joueur pour effectuer cette commande.");
            return false;
        }
        final Player player = (Player) sender;
        if (!player.isOp()) {
            player.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            return false;
        }
        final LunarAPI lunarAPI = this.plugin.getLunarAPI();
        if (lunarAPI == null) {
            player.sendMessage("§3§lMOD§r §7┃§r Le plugin \"Apollo-Bukkit\" est manquant sur le serveur pour activer le mod Xray du Lunar Client");
            return false;
        }
        if (lunarAPI.isPlayerRunningLunarClient(player.getUniqueId())) {
            player.sendMessage("§3§lMOD§r §7┃§r Vous n'utilisez pas le Lunar Client.");
            return false;
        }
        lunarAPI.sendModerationMods(player.getUniqueId());
        player.sendMessage("§3§lMOD§r §7┃§r §f§aVous êtes connecté avec§b§l Lunar Client§a, en tant que spectateur et host, vous avez la possibilité d'utiliser le mod \"X-Ray\" (R+SHIFT ➜ Staff).");
        return true;
    }
}
