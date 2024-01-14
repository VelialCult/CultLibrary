package ru.velialcult.library.java.text;


public class ReplaceData {

    private final String key;

    private final Object object;

    public  ReplaceData(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String getKey() {
        return key;
    }
}
