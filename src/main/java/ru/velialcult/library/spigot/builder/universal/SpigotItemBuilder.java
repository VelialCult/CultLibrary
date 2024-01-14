package ru.velialcult.library.spigot.builder.universal;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import ru.velialcult.library.core.VersionAdapter;
import ru.velialcult.library.core.builder.ItemBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SpigotItemBuilder implements ItemBuilder {

    protected ItemStack itemStack;

    protected ItemMeta itemMeta;

    public SpigotItemBuilder() {

        this.itemStack = new ItemStack(Objects.requireNonNull(XMaterial.matchXMaterial(Material.DIRT).parseMaterial()));
        this.itemMeta = this.itemStack.getItemMeta();
    }

    @Override
    public void update(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    @Override
    public ItemBuilder setDurability(short durability) {
        this.itemStack.setDurability(durability);
        return this;
    }

    @Override
    public ItemBuilder setItem(ItemStack itemstack) {
        this.itemStack = itemstack;
        this.itemMeta = itemStack.getItemMeta();
        return this;
    }

    @Override
    public ItemBuilder addLine(String line) {

        this.itemMeta.getLore().add(VersionAdapter.TextUtil().colorize(line));
        return this;
    }

    @Override
    public ItemBuilder setLore(List<String> lines) {

        this.itemMeta.setLore(VersionAdapter.TextUtil().colorize(lines));
        return this;
    }

    @Override
    public ItemBuilder setLeatherColor(Color color) {

        if(this.itemStack.getType() == Material.LEATHER_CHESTPLATE ||
                this.itemStack.getType() == Material.LEATHER_LEGGINGS ||
                this.itemStack.getType() == Material.LEATHER_BOOTS) {

            try {

                LeatherArmorMeta meta = (LeatherArmorMeta) this.itemMeta;

                meta.setColor(color);

                this.itemStack.setItemMeta(meta);

            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        return this;
    }

    @Override
    public ItemBuilder setDyeColor(DyeColor dyeColor) {

        this.itemStack.setType(Material.valueOf(dyeColor.name() + "_DYE"));

        return this;
    }

    @Deprecated
    @Override
    public ItemBuilder setDyeColor(byte dyeColor) {

        return this;
    }

    @Override
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(Objects.requireNonNull(XEnchantment.matchXEnchantment(enchantment).getEnchant()), level, true);
        return this;
    }

    @Override
    public ItemBuilder removeEnchant(Enchantment enchantment) {

        this.itemMeta.removeEnchant(Objects.requireNonNull(XEnchantment.matchXEnchantment(enchantment).getEnchant()));
        return this;
    }

    @Override
    public ItemBuilder addFlag(ItemFlag flag) {

        this.itemMeta.addItemFlags(flag);

        return this;
    }

    @Override
    public ItemBuilder setUnbreakable(boolean bool) {

        return this;
    }

    @Override
    public ItemBuilder setAmount(long count) {

        this.itemStack.setAmount( (int) count);

        return this;
    }

    @Override
    public ItemBuilder setCustomModelData(int data) {

        if (data != -1) {

            this.itemMeta.setCustomModelData(data);
        }

        return this;
    }

    @Override
    public ItemBuilder setDisplayName(String name) {

        this.itemMeta.setDisplayName(VersionAdapter.TextUtil().colorize(name));

        return this;
    }

    @Override
    public ItemBuilder setType(Material material) {

        this.itemStack.setType(material);

        return this;
    }

    @Override
    public ItemBuilder setType(String material) {
        Optional<XMaterial> optional = XMaterial.matchXMaterial(material);
        optional.ifPresent(xMaterial -> itemStack.setType(Objects.requireNonNull(xMaterial.parseMaterial())));
        this.update(itemStack);

        return this;
    }

    @Override
    public ItemBuilder setMeta(ItemMeta meta) {
        this.itemMeta = meta;

        return this;
    }

    @Override
    public ItemBuilder glowing() {

        if (!this.itemMeta.hasEnchant(Objects.requireNonNull(XEnchantment.ARROW_INFINITE.getEnchant()))) {

            this.addEnchant(XEnchantment.ARROW_INFINITE.getEnchant(), 1);

        } else {

            this.removeEnchant(XEnchantment.ARROW_INFINITE.getEnchant());
        }

        this.addFlag(ItemFlag.HIDE_ENCHANTS);

        return this;
    }

    @Override
    public ItemStack build() {
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }
}
