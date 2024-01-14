package ru.velialcult.library.core.builder;

/**
 * @author Nicholas Alexandrov 20.06.2023
 */
public interface MessageBuilder {

    MessageBuilder append(String text);

    MessageBuilder color(Object color);

    MessageBuilder keyBind(Object keyBind);

    MessageBuilder hoverEvent(String text);

    MessageBuilder clickEvent(Object clickEvent);

     Object build();
}
