package connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.Logs;
import util.Config;

public class DataConnect {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = Config.getDbUrl();
                String user = Config.getDbUser();
                String password = Config.getDbPassword();

                conn = DriverManager.getConnection(url, user, password);
                Logs.info("Соединение с БД установлено.");
            } catch (SQLException e) {
                Logs.error("Ошибка соединения с БД", e);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                conn = null;
                Logs.info("Соединение с БД закрыто.");
            }
        } catch (SQLException e) {
            Logs.error("Ошибка закрытия соединения", e);
        }
    }
}