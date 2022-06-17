package com.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author long
 * @date 2022/6/15 14:00
 * @Description: 连接数据库的类
 */

@SuppressWarnings("all")
public class JDBCUtils {

    private static final String url;
    private static final String userName;
    private static final String password;
    private static final String driver;

    static {

        Properties properties = new Properties();
        InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(is);
            url = properties.getProperty("url");
            userName = properties.getProperty("userName");
            password = properties.getProperty("password");
            driver = properties.getProperty("driver");

            //注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @Description 获取数据库的连接
     * @return
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("无法连接到数据库\n");
        }
    }

    /**
     * @Description 关闭连接
     * @param connection
     */
    public void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description 关闭连接
     * @param connection
     */
    public void close(Connection connection, ResultSet resultSet) {
        try {
            connection.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
