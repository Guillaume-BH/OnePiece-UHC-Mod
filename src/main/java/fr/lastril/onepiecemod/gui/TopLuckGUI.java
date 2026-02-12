package fr.lastril.onepiecemod.gui;

import fr.lastril.onepiecemod.OnePieceMod;
import fr.lastril.onepiecemod.items.CustomItem;
import fr.lastril.onepiecemod.items.pagination.NextItem;
import fr.lastril.onepiecemod.items.pagination.PreviousItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TopLuckGUI implements InventoryProvider {

    private final OnePieceMod plugin;
    private TOP_LUCK_TYPE type = TOP_LUCK_TYPE.DIAMOND;
    private final List<UUID> players = new ArrayList<>();

    public TopLuckGUI(OnePieceMod plugin) {
        this.plugin = plugin;
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.players.add(onlinePlayer.getUniqueId());
        }
    }

    @Override
    public void init(Player player, InventoryContents contents) {

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        final Pagination pagination = contents.pagination();
        pagination.setItemsPerPage(7 * 3);
        final List<ClickableItem> clickableItemList = new ArrayList<>();
        this.players.sort(Comparator.comparingInt(players -> -this.getPlayerValue(players)));
        for (UUID uuid : this.players) {
            final Player target = Bukkit.getPlayer(uuid);
            if (target != null) {
                final CustomItem playerInfo = new CustomItem(Material.SKULL_ITEM, 1,
                        SkullType.PLAYER.ordinal());
                playerInfo.setSkullOwner(target.getName());
                playerInfo.setName("§f" + target.getName());
                playerInfo.addLoreLine("");
                if (this.type == TOP_LUCK_TYPE.GOLD)
                    playerInfo.addLoreLine(" §8┃§e Or miné(s)§f » " + this.getPlayerValue(uuid));
                if (this.type == TOP_LUCK_TYPE.DIAMOND)
                    playerInfo.addLoreLine(" §8┃§b Diamant miné(s)§f » " + this.getPlayerValue(uuid));
                playerInfo.addLoreLine("");
                playerInfo.addLoreLine("§8 » §aCliquez pour vous téléporter");
                playerInfo.addLoreLine("§a     sur ce joueur.");
                playerInfo.addLoreLine("");
                clickableItemList.add(ClickableItem.of(playerInfo
                        .toItemStack(), clickEvent -> {
                    player.teleport(target.getLocation());
                    player.closeInventory();
                }));
            }
        }

        pagination.setItems(clickableItemList.toArray(new ClickableItem[0]));

        final SlotIterator iterator = contents.newIterator(SlotIterator.Type.HORIZONTAL, 2, 1);

        //blacklist border's slots
        for (int i = 1; i < 5; i++) {
            iterator.blacklist(i, 0);
            iterator.blacklist(i, 8);
        }

        pagination.addToIterator(iterator);

        if (!pagination.isFirst())
            contents.set(5, 2, ClickableItem.of(new PreviousItem().toItemStack(),
                    onClick -> contents.inventory().open((Player) onClick.getWhoClicked(),
                            pagination.previous().getPage()))); //previous page

        if (!pagination.isLast())
            contents.set(5, 6, ClickableItem.of(new NextItem().toItemStack(),
                    onClick -> contents.inventory().open((Player) onClick.getWhoClicked(),
                            pagination.next().getPage()))); //next page

        final CustomItem goldItem = new CustomItem(Material.GOLD_BLOCK)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .setName(" §8┃§e Minerais d'ors")
                .addLoreLine("")
                .addLoreLine(" §8┃§f Permet d'afficher la liste par")
                .addLoreLine(" §8┃§f ordre décroissant le nombre de")
                .addLoreLine(" §8┃§f §eminerais d'or§f que chaque joueur")
                .addLoreLine(" §8┃§f à miné.")
                .addLoreLine("")
                .addLoreLine(this.type == TOP_LUCK_TYPE.GOLD ? "§8 » §cFiltre déjà sélectionné !" :
                        "§8 » §aCliquez pour§c changer§f le filtre.")
                .addLoreLine("");

        if (this.type == TOP_LUCK_TYPE.GOLD)
            goldItem.addEnchant(Enchantment.DURABILITY, 1);

        contents.set(0, 3, ClickableItem.of(goldItem
                .toItemStack(), inventoryClickEvent -> this.type = TOP_LUCK_TYPE.GOLD));

        CustomItem diamondItem = new CustomItem(Material.DIAMOND_BLOCK)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .setName(" §8┃§b Diamants")
                .addLoreLine("")
                .addLoreLine(" §8┃§f Permet d'afficher la liste par")
                .addLoreLine(" §8┃§f ordre décroissant le nombre de")
                .addLoreLine(" §8┃§f §bdiamant§f que chaque joueur")
                .addLoreLine(" §8┃§f à miné.")
                .addLoreLine("")
                .addLoreLine(this.type == TOP_LUCK_TYPE.DIAMOND ? "§8 » §cFiltre déjà sélectionné !" :
                        "§8 » §aCliquez pour§c changer§f le filtre.")
                .addLoreLine("");

        if (this.type == TOP_LUCK_TYPE.DIAMOND)
            diamondItem.addEnchant(Enchantment.DURABILITY, 1);

        contents.set(0, 5, ClickableItem.of(diamondItem
                .toItemStack(), inventoryClickEvent -> this.type = TOP_LUCK_TYPE.DIAMOND));
    }

    private int getPlayerValue(UUID uuid) {
        return this.type == TOP_LUCK_TYPE.GOLD ? this.plugin.getGoldsPlayer(uuid) : this.plugin.getDiamondsPlayer(uuid);
    }

    private enum TOP_LUCK_TYPE {
        DIAMOND,
        GOLD
    }

    public void open(Player player) {
        SmartInventory.builder()
                .id("moderation_topluck_" + player.getUniqueId())
                .provider(this)
                .size(6, 9)
                .title("§f(§c!§f) §eTop Luck")
                .closeable(true)
                .build().open(player);
    }


}
