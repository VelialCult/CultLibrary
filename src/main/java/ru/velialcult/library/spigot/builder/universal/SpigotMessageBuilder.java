package ru.velialcult.library.spigot.builder.universal;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import ru.velialcult.library.core.builder.MessageBuilder;

public class SpigotMessageBuilder implements MessageBuilder {

    private final ComponentBuilder builder;

    public SpigotMessageBuilder() {

        this.builder = new ComponentBuilder();
    }

    public SpigotMessageBuilder(String text) {

        this.builder = new ComponentBuilder(text);
    }

    @Override
    public SpigotMessageBuilder append(String text) {

        this.builder.append(text);

        return this;
    }

    @Override
    public SpigotMessageBuilder color(Object color) {

        this.builder.color((ChatColor) color);

        return this;
    }

    @Override
    public SpigotMessageBuilder keyBind(Object keyBind) {

        KeybindComponent keybindComponent = new KeybindComponent((String) keyBind);

        builder.append(keybindComponent);

        return this;
    }

    @Override
    public SpigotMessageBuilder hoverEvent(String text) {

        Text hoverContent = new Text(text);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverContent);

        builder.event(hoverEvent);

        return this;
    }

    public SpigotMessageBuilder clickEvent(Object clickEvent) {

        builder.event((ClickEvent) clickEvent);

        return this;
    }

    public Object build() {

        return builder.create();
    }
}
