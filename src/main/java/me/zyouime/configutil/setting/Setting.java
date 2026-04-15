package me.zyouime.configutil.setting;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import me.zyouime.configutil.ModConfig;

import java.io.File;
import java.lang.reflect.Type;

@Getter
public class Setting<T> {

    @Setter
    private T value;
    private final T defaultValue;
    private final String configKey;
    private final Type type;

    public Setting(String configKey, Type type, T defaultValue) {
        this.configKey = configKey;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public void initValue(JsonElement value, Gson gson) {
        this.value = gson.fromJson(value, this.type);
    }

    public void save(Gson gson, File configFile) {
        JsonObject object = ModConfig.loadConfig(configFile, gson);
        object.add(configKey, gson.toJsonTree(this.value, this.type));
        ModConfig.saveConfig(object, configFile, gson);
    }

    public void reset() {
        this.setValue(defaultValue);
    }
}
