package fr.lastril.onepiecemod.command;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.gui.TopLuckGUI;
import fr.lastril.onepiecemod.launcher.LunarAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopLuckCommand implements CommandExecutor {

    private final OnePieceMod plugin;

    public TopLuckCommand(OnePieceMod plugin) {
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
        new TopLuckGUI(this.plugin).open(player);
        return true;
    }
}
