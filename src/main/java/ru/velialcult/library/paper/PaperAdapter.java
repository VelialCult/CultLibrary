package ru.velialcult.library.paper;

import ru.velialcult.library.core.util.SkullUtils;
import ru.velialcult.library.paper.utils.PaperSkullUtil;
import ru.velialcult.library.spigot.SpigotAdapter;

/**
 * @author Nicholas Alexandrov 27.06.2023
 */
public class PaperAdapter extends SpigotAdapter {

    private final SkullUtils skullUtils;


    {

        skullUtils = new PaperSkullUtil();

    }

    @Override
    public SkullUtils SkullUtils() {
        return skullUtils;
    }

}
