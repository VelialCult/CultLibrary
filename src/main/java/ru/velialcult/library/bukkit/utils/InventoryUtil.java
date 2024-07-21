package ru.velialcult.library.bukkit.utils;

import com.cryptomorin.xseries.XMaterial;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.java.text.ReplaceData;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AutoUpdateItem;
import xyz.xenondevs.invui.item.impl.SuppliedItem;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class InventoryUtil {

    public static <T, E extends Enum<E>> SuppliedItem createChangeModuleItem(
            T object,
            Function<T, E> getter,
            BiConsumer<T, E> setter,
            String displayName,
            List<String> description,
            Class<E> enumClass
    ) {
        List<String> types = new ArrayList<>();
        for (E moduleType : enumClass.getEnumConstants()) {
            if (getter.apply(object) == moduleType) {
                types.add(" §a▹ " + moduleType.name());
            }
            else {
                types.add(" §8◦ " + moduleType.name());
            }
        }

        return new AutoUpdateItem(20, () ->
                s -> VersionAdapter.getSkullBuilder()
                        .setDisplayName(displayName)
                        .setLore(VersionAdapter.TextUtil().setReplaces(description, new ReplaceData("{type}", String.join("\n", types))))
                        .setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTk0YjNmOTc1ODk3YjdmY2VhMTBlOWIzNTEyMWY4MDhkYTIzYTk0NzJmY2EyODA0MTczYWI0YWFkZmE4NDcwOCJ9fX0=")
                        .build()) {
            @Override
            public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
                E currentType = getter.apply(object);
                E[] constants = enumClass.getEnumConstants();
                int currentIndex = currentType.ordinal();
                int nextIndex = (currentIndex + 1) % constants.length;
                E nextType = constants[nextIndex];
                types.clear();
                setter.accept(object, nextType);
                for (E moduleType : constants) {
                    if (getter.apply(object) == moduleType) {
                        types.add(" §a▹ " + moduleType.name());
                    }
                    else {
                        types.add(" §8◦ " + moduleType.name());
                    }
                }
            }
        };
    }

    public static <T> SuppliedItem createBooleanSettingItem(String displayName, List<String> description, T object, Function<T, Boolean> optionGetter, BiConsumer<T, Boolean> optionSetter) {
        return new AutoUpdateItem(20, () -> {
            Material material = optionGetter.apply(object) ? XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial() : XMaterial.RED_STAINED_GLASS_PANE.parseMaterial();
            String setting = optionGetter.apply(object) ? "§aВключено" : "§cОтключено";

            ItemStack itemStack = VersionAdapter.getItemBuilder()
                    .setType(material)
                    .setDisplayName(displayName)
                    .setLore(VersionAdapter.TextUtil().setReplaces(new ArrayList<>(Arrays.asList(
                            "",
                            "{description}",
                            "",
                            " §8▪ §fТекущая настройка: " + setting,
                            "",
                            "§8▹ §eНажмите, чтобы изменить"
                    )), new ReplaceData("{description}", String.join("\n", getColoredLore(description, "", "")))))
                    .build();
            return new ItemBuilder(itemStack);
        }) {
            @Override
            public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
                optionSetter.accept(object, !optionGetter.apply(object));
            }
        };
    }

    public static List<String> getColoredLore(List<String> list, String symbol, String text) {
        if (list.isEmpty()) {
            return new ArrayList<>(Arrays.asList(symbol + " §cНет"));
        }

        List<String> commandsText = new ArrayList<>();

        for (String cmd : list) {
            String effectText = symbol + "§f " + text + "§e" + cmd;

            commandsText.add(effectText);
        }

        return commandsText;
    }

    public static List<String> getColoredSelectLore(List<String> lore, int selectedIndex) {
        List<String> coloredLore = new ArrayList<>();
        for (int i = 0; i < lore.size(); i++) {
            if (i == selectedIndex) {
                coloredLore.add(" §a▹ " + lore.get(i));
            }
            else {
                coloredLore.add(" §8◦ " + lore.get(i));
            }
        }
        return coloredLore;
    }

    public static ItemProvider createItem(Player player, String path, FileConfiguration fileConfiguration, ReplaceData... replaceData) {
        ItemStack itemStack;
        String materialName = fileConfiguration.getString(path + ".item.material");
        String url = fileConfiguration.getString(path + ".item.head");
        String displayName = PlaceholderAPI.setPlaceholders(player, VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getString(path + ".displayName"), replaceData)));
        List<String> lore = PlaceholderAPI.setPlaceholders(player, VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getStringList(path + ".lore"), replaceData)));
        int customModelData = fileConfiguration.getInt(path + ".customModelData", 0);

        if (materialName.equalsIgnoreCase("head")) {
            itemStack = VersionAdapter.getSkullBuilder()
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setTexture(url)
                    .build();

        }
        else {
            itemStack = VersionAdapter.getItemBuilder()
                    .setType(materialName)
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setCustomModelData(customModelData)
                    .build();
        }

        return new ItemBuilder(itemStack);
    }

    public static void setItems(Gui.Builder builder, Map<Character, SuppliedItem> suppliedItems) {
        suppliedItems.forEach(builder::addIngredient);
    }

    public static Map<Character, SuppliedItem> createItems(
            Player player,
            FileConfiguration config,
            String path,
            BiConsumer<InventoryClickEvent, String> clickHandler,
            ReplaceData... replaceData
    ) {
        Map<Character, SuppliedItem> baseCustomItems = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                String itemPath = path + "." + key;
                if (config.getBoolean(itemPath + ".custom")) {
                    if (config.getBoolean(itemPath + ".autoUpdate", false)) {
                        AutoUpdateItem customItem = createAutoUpdateItem(player, config, itemPath, clickHandler, replaceData);
                        char symbol = config.getString(itemPath + ".symbol").charAt(0);
                        baseCustomItems.putIfAbsent(symbol, customItem);
                    } else {
                        SuppliedItem customItem = createItem(player, config, itemPath, clickHandler, replaceData);
                        char symbol = config.getString(itemPath + ".symbol").charAt(0);
                        baseCustomItems.putIfAbsent(symbol, customItem);
                    }
                }
            }
        }
        return baseCustomItems;
    }


    public static Map<Character, SuppliedItem> createItems(
            FileConfiguration config,
            String path,
            BiConsumer<InventoryClickEvent, String> clickHandler,
            ReplaceData... replaceData
    ) {
        Map<Character, SuppliedItem> baseCustomItems = new HashMap<>();
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                String itemPath = path + "." + key;
                if (config.getBoolean(itemPath + ".custom")) {
                    if (config.getBoolean(itemPath + ".autoUpdate", false)) {
                        AutoUpdateItem customItem = createAutoUpdateItem(null, config, itemPath, clickHandler, replaceData);
                        char symbol = config.getString(itemPath + ".symbol").charAt(0);
                        baseCustomItems.putIfAbsent(symbol, customItem);
                    } else {
                        SuppliedItem customItem = createItem(null, config, itemPath, clickHandler, replaceData);
                        char symbol = config.getString(itemPath + ".symbol").charAt(0);
                        baseCustomItems.putIfAbsent(symbol, customItem);
                    }
                }
            }
        }
        return baseCustomItems;
    }

    public static AutoUpdateItem createAutoUpdateItem(Player player, FileConfiguration config,
                                                      String itemPath,
                                                      BiConsumer<InventoryClickEvent, String> clickHandler,
                                                      ReplaceData... replaceData) {
        return new AutoUpdateItem(20, () ->
                s -> createItem(player, config, itemPath, replaceData)) {
            @Override
            public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                clickHandler.accept(event, itemPath);
            }
        };
    }

    public static SuppliedItem createItem(
            Player player,
            FileConfiguration config,
            String itemPath,
            BiConsumer<InventoryClickEvent, String> clickHandler,
            ReplaceData... replaceData) {
        return new SuppliedItem(
                () -> s -> createItem(player, config, itemPath, replaceData),
                click -> {
                    clickHandler.accept(click.getEvent(), itemPath);
                    return true;
                }
        );
    }

    public static ItemStack createItem(Player player, FileConfiguration fileConfiguration, String path, ReplaceData... replaceData) {
        ItemStack itemStack;
        String materialName = fileConfiguration.getString(path + ".item.material");
        String url = fileConfiguration.getString(path + ".item.head");
        String displayName = VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getString(path + ".displayName"), replaceData));
        List<String> lore = VersionAdapter.TextUtil().colorize(VersionAdapter.TextUtil().setReplaces(fileConfiguration.getStringList(path + ".lore"), replaceData));

        if (player != null) {
            displayName = PlaceholderAPI.setPlaceholders(player, displayName);
            lore = PlaceholderAPI.setPlaceholders(player, lore);
        }

        int customModelData = fileConfiguration.getInt(path + ".customModelData", 0);

        if (materialName.equalsIgnoreCase("head")) {
            itemStack = VersionAdapter.getSkullBuilder()
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setTexture(url)
                    .build();

        } else {
            itemStack  = VersionAdapter.getItemBuilder()
                    .setType(materialName)
                    .setDisplayName(displayName)
                    .setLore(lore)
                    .setCustomModelData(customModelData)
                    .build();
        }

        return itemStack;
    }
}
