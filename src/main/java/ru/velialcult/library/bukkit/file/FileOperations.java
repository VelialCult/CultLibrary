package ru.velialcult.library.bukkit.file;

import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.java.text.ReplaceData;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileOperations {

    private final Map<String, String> map;

    public FileOperations(Map<String, String> map) {
        this.map = map;
    }

    public boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    public boolean getBoolean(String path, boolean defValue) {
        String value = map.get(path);

        if (value == null)
            return defValue;

        return Boolean.parseBoolean(value);
    }

    public int getInt(String path, int defValue) {
        String value = map.get(path);

        if (value == null)
            return defValue;

        return Integer.parseInt(value);
    }

    public int getInt(String path) {
        return getInt(path, 0);
    }

    public double getDouble(String path, double defValue) {
        String value = map.get(path);

        if (value == null)
            return defValue;

        return Double.parseDouble(value);
    }

    public double getDouble(String path) {
        return getDouble(path, 0.0D);
    }

    public List<String> getList(String path, ReplaceData... replacesData) {
        String value = map.get(path);

        if (value == null) {
            return Arrays.asList("&cНе удалось получить список из конфиугурации.");
        }

        List<String> result = Arrays.stream(value.split("\n"))
                .map(line -> line.replace("[", "").replace("]", "").replace("\n", ""))
                .collect(Collectors.toList());

        result = VersionAdapter.TextUtil().setReplaces(result, replacesData);
        return VersionAdapter.TextUtil().colorize(result);
    }

    public String getString(String path, String defValue, ReplaceData... replacesData) {
        String value = map.get(path);

        if (value == null) {
            return VersionAdapter.TextUtil().colorize(defValue);
        }

        value = VersionAdapter.TextUtil().setReplaces(value, replacesData);
        return VersionAdapter.TextUtil().colorize(value);
    }

    public String getString(String path, ReplaceData... replacesData) {
        return getString(path, "&cНе удалось получить сообщение из конфигурации", replacesData);
    }
}
