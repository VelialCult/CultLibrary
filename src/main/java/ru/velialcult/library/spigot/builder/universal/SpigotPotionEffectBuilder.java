package ru.velialcult.library.spigot.builder.universal;

import com.cryptomorin.xseries.XPotion;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.velialcult.library.core.builder.PotionEffectBuilder;

import java.util.Optional;


/**
 * Класс-билдер для создания эффектов зелий для версии 1.16
 *
 * @author Nicholas Alexandrov 01.06.2023
 */
public class SpigotPotionEffectBuilder implements PotionEffectBuilder {

    protected boolean ambient;

    protected int amplifier;

    protected int duration;

    protected boolean particles;

    protected PotionEffectType type;

    private boolean icon;

    @Override
    public PotionEffectBuilder setAmbient(boolean bool) {

        this.ambient = bool;

        return this;
    }

    @Override
    public PotionEffectBuilder setAmplifier(int amplifier) {

        this.amplifier = amplifier;

        return this;
    }

    @Override
    public PotionEffectBuilder setDuration(int seconds) {

        this.duration = seconds;

        return this;
    }

    @Override
    public PotionEffectBuilder setIcon(boolean icon) {

        this.icon = icon;

        return this;
    }

    @Override
    public PotionEffectBuilder setParticles(boolean particles) {

        this.particles = particles;

        return this;
    }

    @Override
    public PotionEffectBuilder setType(PotionEffectType type) {

        this.type = type;

        return this;
    }

    @Override
    public PotionEffectBuilder setType(String type) {

        Optional<XPotion> optional = XPotion.matchXPotion(type);

        optional.ifPresent(xPotion -> this.type = xPotion.getPotionEffectType());

        return this;
    }

    @Deprecated
    @Override
    public PotionEffectBuilder setColor(Color color) {
        return this;
    }

    @Override
    public PotionEffect build() {
        return new PotionEffect(this.type, this.duration, this.amplifier, this.ambient, this.particles, icon);
    }

}
