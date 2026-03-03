package me.zyouime.configutil.setting;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.zyouime.configutil.ModConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ModSettings {

    public List<Setting<?>> settings = new ArrayList<>();
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

    public void loadSettings() {
        if (settings.isEmpty()) {
            return;
        }
        boolean isExists = configFile.exists();
        JsonObject config = isExists ? ModConfig.loadConfig(configFile, gson) : new JsonObject();
        for (Setting<?> setting : settings) {
            String configKey = setting.getConfigKey();
            if (!config.has(configKey)) {
                config.add(configKey, gson.toJsonTree(setting.getDefaultValue()));
            }
            setting.initValue(config.get(configKey).deepCopy(), gson);
        }
        if (!isExists) {
            ModConfig.saveConfig(config, configFile, gson);
        }
    }

    public void saveSettings() {
        settings.forEach(setting -> setting.save(gson, configFile));
    }
}
