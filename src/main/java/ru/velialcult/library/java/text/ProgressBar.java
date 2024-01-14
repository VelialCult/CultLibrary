package ru.velialcult.library.java.text;

public class ProgressBar {

    public static String createBar(double current,
                                   double max,
                                   int symbolCount,
                                   String yesColor,
                                   String noColor,
                                   String symbol) {
        double tenPercent = (current / max) * symbolCount;
        int percent = (int) Math.round(tenPercent);
        StringBuilder bar = new StringBuilder();
        bar.append(yesColor);
        for (int i = 0; i < percent && bar.length() < symbolCount; i++) {
            bar.append(symbol);
        }
        bar.append(noColor);
        for (int i = 0; i < symbolCount - percent && bar.length() < symbolCount; i++) {
            bar.append(symbol);
        }
        return bar.toString();
    }

    public static String getPercent(double current, double max) {
        double allPercent = (current / max) * 100;
        return ((int) Math.round(allPercent)) + "%";
    }
}
