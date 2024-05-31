package ru.velialcult.library.spigot.utils.universal;

import net.md_5.bungee.api.ChatColor;
import ru.velialcult.library.bukkit.utils.VersionsUtil;
import ru.velialcult.library.java.text.ReplaceData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nicholas Alexandrov 15.06.2023
 */
public class SpigotTextUtil  {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");

    public String colorize(String string) {

        if (VersionsUtil.getServerVersion().isNewerEqualThanV1_16()) {
            Matcher matcher = HEX_PATTERN.matcher(string);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                String color = matcher.group();
                matcher.appendReplacement(buffer, ChatColor.of(color.substring(1)).toString());
            }

            matcher.appendTail(buffer);
            return ChatColor.translateAlternateColorCodes('&', buffer.toString());

        } else {
            return ChatColor.translateAlternateColorCodes('&', string);
        }
    }

    public String decColorize(String string) {
        return ChatColor.stripColor(string);
    }

    public List<String> colorize(List<String> list) {
        list.replaceAll(this::colorize);
        return list;
    }

    public List<String> deColorize(List<String> list) {
        list.replaceAll(this::decColorize);
        return list;
    }

    public List<String> setReplaces(List<String> lore, ReplaceData... replacesData) {
        List<String> result = new ArrayList<>();
        for (String str : lore) {
            String replaced = setReplaces(str, replacesData);
            replaced = replaced.replace("[", "").replace("]", "");
            String[] lines = replaced.split("\n");
            result.addAll(Arrays.asList(lines));
        }
        return result;
    }

    public String setReplaces(String text, ReplaceData... replacesData) {
        for (ReplaceData replaceData : replacesData) {
            if (replaceData.getKey() == null || replaceData.getObject() == null) continue;
            text = text.replace(replaceData.getKey(), replaceData.getObject().toString());
        }

        return text;
    }
 }
