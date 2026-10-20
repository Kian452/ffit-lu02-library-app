package ch.bzz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties properties;

    public static String get(String key) {
        if (properties == null) {
            properties = load();
        }
        return properties.getProperty(key);
    }

    private static Properties load() {
        Properties loaded = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            loaded.load(input);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not load config.properties. Copy config.properties.template to config.properties and fill in the values.",
                    e);
        }

        return loaded;
    }
}
