package ru.velialcult.library.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;
import ru.velialcult.library.bukkit.utils.items.ItemUtil;
import ru.velialcult.library.core.VersionAdapter;

import java.util.Objects;

public class PlayerUtil {

    public static void giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1)
            Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName())).dropItem(player.getLocation(), itemStack);
        else
            player.getInventory().addItem(itemStack);
        player.updateInventory();
    }

    public static boolean senderIsPlayer(CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getLogger().warning("Эту команду может выполнять только игрок");
            return false;
        }
        return true;
    }

    public static int getItemsAmount(Player player, ItemStack itemStack) {
        PlayerInventory inventory = player.getInventory();
        int amount = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content == null) {
                continue;
            }
            if (ItemUtil.areSimilar(content, itemStack)) {
                amount += content.getAmount();
            }
        }
        return amount;
    }

    public static void removeItems(Player player, ItemStack itemStack) {
        int amount = itemStack.getAmount();
        if (getItemsAmount(player, itemStack) < amount) return;
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            ItemStack content = player.getInventory().getItem(i);
            if (content != null) {
                int contentAmount = content.getAmount();
                if (ItemUtil.areSimilar(content, itemStack)) {
                    if (amount >= contentAmount) {
                        content.setAmount(0);
                        player.updateInventory();
                        amount -= contentAmount;

                    }
                    else {
                        content.setAmount(contentAmount - amount);
                        player.updateInventory();
                        amount = 0;
                    }
                }
            }
        }
    }
}
