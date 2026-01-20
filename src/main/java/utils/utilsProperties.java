package utils;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.System.getProperty;

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

    static {
        loadProperties("allure.properties");
    }

    private static void loadProperties(String fileName) {
        try (InputStream input = utilsProperties.class.getClassLoader()
                .getResourceAsStream(fileName)) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Failed to load properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key, "false"));
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
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