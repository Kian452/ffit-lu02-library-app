package ch.bzz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    public static Properties load() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not load config.properties. Copy config.properties.template to config.properties and fill in the values.",
                    e);
        }

        return properties;
    }
}
