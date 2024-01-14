package ru.velialcult.library.spigot.builder.universal;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.SkullBuilder;

import java.util.List;
import java.util.Objects;

/**
 *
 * Класс для создания голов для версии 1.15
 *
 * @author Nicholas Alexandrov 27.05.2023
 */
public class SpigotSkullBuilder implements SkullBuilder {

    private final ItemStack itemStack;

    protected final SkullMeta skullMeta;

    public SpigotSkullBuilder() {
        assert XMaterial.PLAYER_HEAD.parseMaterial() != null;
        this.itemStack = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1);
        this.skullMeta = (SkullMeta)this.itemStack.getItemMeta();
    }

    @Override
    public SkullBuilder setTexture(String texture) {
        VersionAdapter.SkullUtils().setTexture(this.skullMeta, texture);
        return this;
    }

    @Override
    public SkullBuilder setDisplayName(String displayName) {
        this.skullMeta.setDisplayName(VersionAdapter.TextUtil().colorize(displayName));
        return this;
    }

    @Override
    public SkullBuilder setLore(List<String> lore) {
        this.skullMeta.setLore(VersionAdapter.TextUtil().colorize(lore));
        return this;
    }

    @Override
    public SkullBuilder addLine(String line) {
        this.skullMeta.getLore().add(VersionAdapter.TextUtil().colorize(line));
        return this;
    }

    @Override
    public SkullBuilder setOwner(OfflinePlayer offlinePlayer) {
        this.skullMeta.setOwningPlayer(offlinePlayer);
        return this;
    }

    @Override
    public SkullBuilder addEnchant(Enchantment enchantment, int level) {
        this.skullMeta.addEnchant(Objects.requireNonNull(XEnchantment.matchXEnchantment(enchantment).getEnchant()), level, true);
        return this;
    }

    @Override
    public SkullBuilder removeEnchant(Enchantment enchantment) {
        this.skullMeta.removeEnchant(Objects.requireNonNull(XEnchantment.matchXEnchantment(enchantment).getEnchant()));
        return this;
    }

    @Override
    public SkullBuilder addFlag(ItemFlag flag) {
        this.skullMeta.addItemFlags(flag);
        return this;
    }

    @Override
    public SkullBuilder setUnbreakable(boolean bool) {
        this.skullMeta.setUnbreakable(bool);
        return this;
    }

    @Override
    public ItemStack build() {
        this.itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}
