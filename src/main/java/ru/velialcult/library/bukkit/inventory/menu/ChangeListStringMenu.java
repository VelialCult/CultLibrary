package ru.velialcult.library.bukkit.inventory.menu;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import ru.velialcult.library.bukkit.inventory.LoreHolder;
import ru.velialcult.library.bukkit.inventory.PlayerInputHandler;
import ru.velialcult.library.bukkit.utils.InventoryUtil;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.java.text.ReplaceData;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.impl.AutoUpdateItem;
import xyz.xenondevs.invui.item.impl.SuppliedItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChangeListStringMenu {

    public static void generateInventory(Player player,
                                         LoreHolder loreHolder,
                                         Runnable closeRun) {
        List<String> lore = loreHolder.getLore();
        AtomicInteger index = new AtomicInteger();
        SuppliedItem info = new AutoUpdateItem(20, () ->
                s -> VersionAdapter.getItemBuilder()
                        .setType(XMaterial.BOOK.parseMaterial())
                        .setDisplayName("§6Текущее описание")
                        .setLore(VersionAdapter.TextUtil().setReplaces(new ArrayList<>(Arrays.asList(
                                "",
                                " §8▪ §fТекущее описание задачи:",
                                "",
                                "{lore}",
                                "",
                                "§8▹ §eНажмите КОЛЁСИКО, чтобы редактивровать строку",
                                "§8▹ §eНажмите ПКМ, чтобы перейти к следующей строке",
                                "§8▹ §eНажмите ЛКМ, чтобы удалить строку"
                        )), new ReplaceData("{lore}", String.join("\n", InventoryUtil.getColoredSelectLore(lore, index.get()))))).build()) {
            @Override
            public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
                if (clickType == ClickType.LEFT) {
                    if (!lore.isEmpty()) {
                        lore.remove(index.get());
                        index.set(index.get() % lore.size());
                    }
                }
                else if (clickType == ClickType.RIGHT) {
                    index.set((index.get() + 1) % lore.size());
                } else if (clickType == ClickType.MIDDLE) {
                    player.closeInventory();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " {\"text\":\"§6➤ §fНажмите здесь, чтобы начать редактивровать строку §7[КЛИК]\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + lore.get(index.get()) + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§6➤ §fНажмите, чтобы вставить строку описания в чат\"}}");
                    PlayerInputHandler.addPlayer(player, (str) -> {
                        lore.set(index.get(), str);
                        generateInventory(player, loreHolder, closeRun);
                    });
                }
            }
        };

        SuppliedItem add = new SuppliedItem( () ->
                s -> VersionAdapter.getItemBuilder()
                        .setType(XMaterial.PAPER.parseMaterial())
                        .setDisplayName("§6Добавить строку")
                        .setLore(new ArrayList<>(Arrays.asList(
                                "",
                                "§fИспользуйте данную кнопку для добавления",
                                "§fСтроки в описание предмета",
                                "",
                                "§8▹ §eНажмите, чтобы сохранить"
                        ))).build(), click -> {
            VersionAdapter.MessageUtils().sendMessage(player, "§6➤ §fВведите, пожалуйста, новую строку к описанию в §6§lчат");
            player.closeInventory();
            PlayerInputHandler.addPlayer(player, (str) -> {
                lore.add(str);
                generateInventory(player, loreHolder, closeRun);
            });
            return true;
        });

        SuppliedItem saveButton = new SuppliedItem(() ->
                                                           s -> VersionAdapter.getItemBuilder()
                                                                   .setDisplayName("§6Сохранить описание")
                                                                   .setLore(new ArrayList<>(Arrays.asList(
                                                                           "",
                                                                           "§7Нажмите, чтобы сохранить настройки",
                                                                           "§7Описания.",
                                                                           "",
                                                                           " §8▹ §eНажмите, чтобы сохранить"
                                                                   )))
                                                                   .setType(XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial())
                                                                   .build(), click -> {
            loreHolder.setLore(lore);
            closeRun.run();
            return true;
        });

        Gui gui = Gui.normal()
                .setStructure(
                        ". . . . i . . . .",
                        ". . . . . . . . .",
                        ". . a . . . s . .",
                        ". . . . . . . . .",
                        ". . . . o . . . ."
                )
                .addIngredient('i', info)
                .addIngredient('a', add)
                .addIngredient('s', saveButton)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setTitle("§8Настройка задачи")
                .setGui(gui)
                .build();
        window.open();
    }
}
