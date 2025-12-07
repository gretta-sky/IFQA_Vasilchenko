package utils;


import java.io.InputStream;
import java.util.Properties;

public class utilsProperties {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = utilsProperties.class.getClassLoader()
                .getResourceAsStream("selenide.properties")) {
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getUsername() {
        return get("username");
    }

    public static String getPassword() {
        return get("password");
    }
}