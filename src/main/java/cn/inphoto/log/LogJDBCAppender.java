package cn.inphoto.log;

import org.apache.log4j.jdbc.JDBCAppender;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * Log4j数据库管理，判断数据库链接是否有效
 * Created by kaxia on 2017/6/21.
 */
public class LogJDBCAppender extends JDBCAppender {

    @Override
    protected Connection getConnection() throws SQLException {
        // TODO Auto-generated method stub
        if (!DriverManager.getDrivers().hasMoreElements()) setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
// 判断连接是否实效,如果失效,则重新获取连接

        try {
            connection.isClosed();
        } catch (Exception e) {
            System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "LOG4J连接失效，将重新获取连接");
            connection = null;
        }
        if (connection == null) {
            connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
        }
        return connection;
    }
}
