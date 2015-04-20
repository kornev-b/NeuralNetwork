package me.neuralnetwork.core.util;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a general set of properties for neuroph objects
 */
public class Properties extends HashMap<String, Object> {
    protected void createKeys(String... keys) {
        for (String key : keys) {
            put(key, "");
        }
    }

    public void setProperty(String key, Object value) {
        put(key, value);
    }

    public Object getProperty(String key) {
        return this.get(key);
    }

    public boolean hasProperty(String key) {
        return this.containsKey(key);
    }

}
