package me.zyouime.configutil.setting;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.zyouime.configutil.ModConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ModSettings {

    private final List<Setting<?>> settings = new ArrayList<>();
    private final File configFile;
    private final Gson gson;

    public ModSettings(File configFile, Gson gson) {
        this.configFile = configFile;
        this.gson = gson;
    }

    public <T extends Setting<?>> T registerSetting(T setting) {
        this.settings.add(setting);
        return setting;
    }

    public JsonObject loadConfig() {
        return ModConfig.loadConfig(configFile, gson);
    }

    public void loadSettings() {
        if (settings.isEmpty()) {
            return;
        }
        JsonObject config = loadConfig();
        boolean changed = false;
        for (Setting<?> setting : settings) {
            String configKey = setting.getConfigKey();
            if (!config.has(configKey) || config.get(configKey).isJsonNull()) {
                config.add(configKey, gson.toJsonTree(setting.getDefaultValue()));
                changed = true;
            }
            setting.initValue(config.get(configKey).deepCopy(), gson);
        }
        if (changed) {
            ModConfig.saveConfig(config, configFile, gson);
        }
    }

    public void saveSettings() {
        JsonObject config = loadConfig();
        for (Setting<?> setting : settings) {
            setting.save(gson, config);
        }
        ModConfig.saveConfig(config, configFile, gson);
    }
}
