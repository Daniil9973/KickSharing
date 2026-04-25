package util;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("resources/config.properties")) {
            if (input == null) {
                Logs.info("config.properties не найден, используются значения по умолчанию");
                props.setProperty("db.url", "jdbc:postgresql://localhost:5432/KickSharing");
                props.setProperty("db.user", "postgres");
                props.setProperty("db.password", "");
            } else {
                props.load(input);
            }
        } catch (Exception e) {
            Logs.error("Ошибка подстановки данных Config", e);
        }
    }

    public static String getDbUrl() {
        return props.getProperty("db.url");
    }

    public static String getDbUser() {
        return props.getProperty("db.user");
    }

    public static String getDbPassword() {
        return props.getProperty("db.password");
    }
}