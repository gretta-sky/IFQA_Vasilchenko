package utils;

import java.io.InputStream;
import java.util.Properties;

public class utilsProperties {
    private static final Properties properties = new Properties();

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

    public static boolean getScreenshots() {
        return Boolean.parseBoolean(get("allure.selenide.screenshots"));
    }

    public static boolean getPageSource() {
        return Boolean.parseBoolean(get("allure.selenide.savePageSource"));
    }

    public static boolean getSelenideSteps() {
        return Boolean.parseBoolean(get("allure.selenide.includeSelenideSteps"));
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getBaseUri() {
        return get("base.uri");
    }

    public static String getNameChar() {
        return get("name.search");
    }

    public static String getExpBodySuccess() {
        return get("exp.body.success");
    }

    public static String getExpBodeNeg() {
        return get("exp.body.neg");
    }

    public static Integer getExpStatus() {
        return Integer.parseInt(get("exp.success.status"));
    }

    public static Integer getExpStatusNeg() {
        return Integer.parseInt(get("exp.neg.status"));
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}