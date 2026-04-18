package me.zyouime.configutil.setting;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.zyouime.configutil.ModConfig;

import java.io.File;
import java.lang.reflect.Type;
import java.util.function.Consumer;

@Getter
public class Setting<T> {

    private T value;
    private final T defaultValue;
    private final String configKey;
    private final Type type;
    @Setter private Consumer<T> setCallback;

    public Setting(String configKey, Type type, T defaultValue, Consumer<T> setCallback) {
        this.configKey = configKey;
        this.defaultValue = defaultValue;
        this.type = type;
        this.setCallback = setCallback;
    }

    public Setting(String configKey, Type type, T defaultValue) {
        this(configKey, type, defaultValue, null);
    }

    public void initValue(JsonElement value, Gson gson) {
        this.value = gson.fromJson(value, this.type);
    }

    public void save(Gson gson, File configFile) {
        JsonObject object = ModConfig.loadConfig(configFile, gson);
        object.add(configKey, gson.toJsonTree(this.value, this.type));
        ModConfig.saveConfig(object, configFile, gson);
    }

    public void setValue(T value) {
        if (setCallback != null) {
            setCallback.accept(value);
        }
        this.value = value;
    }

    public void reset() {
        this.setValue(defaultValue);
    }
}
