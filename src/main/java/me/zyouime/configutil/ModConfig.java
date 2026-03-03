package me.zyouime.configutil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.zyouime.configutil.adapter.ColorAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ModConfig {

    public static final Gson DEFAULT_GSON = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Color.class, new ColorAdapter()).create();

    public static JsonObject loadConfig(File configFile, Gson gson) {
        try (FileReader fileReader = new FileReader(configFile)) {
            return gson.fromJson(fileReader, JsonObject.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfig(JsonObject object, File configFile, Gson gson) {
        try (FileWriter fileWriter = new FileWriter(configFile)) {
            gson.toJson(object, fileWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
