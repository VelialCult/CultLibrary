package ru.velialcult.library.spigot.builder.universal;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.PotionBuilder;

import java.util.List;
import java.util.Objects;

/**
 *
 * Класс для создания зелий для версии 1.18
 *
 * @author Nicholas Alexandrov 27.05.2023
 */
public class SpigotPotionBuilder implements PotionBuilder {

    private final ItemStack itemStack;

    protected PotionMeta potionMeta;

    protected PotionData potionData;

    public SpigotPotionBuilder() {

        this.itemStack = new ItemStack(Objects.requireNonNull(XMaterial.POTION.parseMaterial()));

        this.potionMeta = (PotionMeta) this.itemStack.getItemMeta();

        assert this.potionMeta != null;

        this.potionData = this.potionMeta.getBasePotionData();
    }

    @Override
    public PotionBuilder setDisplayName(String displayName) {

        this.potionMeta.setDisplayName(VersionAdapter.TextUtil().colorize(displayName));

        return this;
    }

    @Override
    public PotionBuilder setLore(List<String> lore) {

        this.potionMeta.setLore(VersionAdapter.TextUtil().colorize(lore));

        return this;
    }

    @Override
    public PotionBuilder addLine(String line) {

        this.potionMeta.getLore().add(VersionAdapter.TextUtil().colorize(line));

        return this;
    }

    @Override
    public PotionBuilder setColor(Color color) {

        this.potionMeta.setColor(color);

        return this;
    }

    @Override
    public PotionBuilder setAmount(int amount) {

        this.itemStack.setAmount(amount);

        return this;
    }

    @Override
    public PotionBuilder addCustomEffect(PotionEffect effect) {

        this.potionMeta.addCustomEffect(effect, true);

        return this;
    }

    @Override
    public PotionBuilder setType(PotionType type, boolean extended, boolean upgraded) {

        this.potionData = new PotionData(type, extended, upgraded);

        this.potionMeta.setBasePotionData(potionData);

        return this;
    }


    @Override
    public ItemStack build() {

        this.itemStack.setItemMeta(itemStack.getItemMeta());

        return this.itemStack;
    }
}
