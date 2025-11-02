package com.rageelement.configuration;

import com.rageelement.RageElement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    private final Path path;
    private final FileConfiguration config;

    public Config(String name) {
        path = Paths.get(RageElement.getPlugin().getDataFolder().toString(), name);
        config = YamlConfiguration.loadConfiguration(path.toFile());
        reloadConfig();
    }

    public void createConfig() {
        try {
            Files.createFile(path);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        if (Files.notExists(path)) {
            createConfig();
        }

        try {
            config.load(path.toFile());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void newConfig(String name) {
        new Config(name);
    }
}
