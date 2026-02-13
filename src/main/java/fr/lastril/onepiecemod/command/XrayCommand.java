package fr.lastril.onepiecemod.command;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.launcher.LunarAPI;
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
            player.sendMessage(this.plugin.getMessage("no-permission"));
            return false;
        }
        final LunarAPI lunarAPI = this.plugin.getLunarAPI();
        if (lunarAPI == null) {
            player.sendMessage(this.plugin.getMessage("xray-apollo-missing"));
            return false;
        }
        if (!lunarAPI.isPlayerRunningLunarClient(player.getUniqueId())) {
            player.sendMessage(this.plugin.getMessage("xray-no-running-lunar"));
            return false;
        }
        lunarAPI.sendModerationMods(player.getUniqueId());
        player.sendMessage(this.plugin.getMessage("xray-send"));
        return true;
    }
}
