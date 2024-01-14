package ru.velialcult.library.java.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Nicholas Alexandrov 18.06.2023
 */
public class MathUtil {

    private static final Random random = new Random();

    public static int randomInt() {

        return random.nextInt(Integer.MAX_VALUE);
    }

    public static double randomDouble(double min, double max) {

        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static int randomInt(int min, int max) {

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}