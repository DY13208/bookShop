package bookshop.mySpringMvc.Util;


import bookshop.mySpringMvc.JDBCUtil.JdbcDbUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Alitar
 * @date 2023-02-01 19:59
 */
public class ConnectionUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static String user = null;
    private static String password = null;
    private static String url = null;
    private static String driverClass = null;
//jdbc
    public static Connection getConn() {
        Connection connection = threadLocal.get();
        if (connection == null){
            connection = createConnection();
            threadLocal.set(connection);
        }

        return threadLocal.get();
    }
    private static Connection createConnection(){

        try {
            InputStream resourceAsStream = JdbcDbUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);

            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);

            return DriverManager.getConnection(url,user,password);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void closeConn(){
        Connection connection = threadLocal.get();
        if (connection == null){
            return ;
        }
        try {
            if (!connection.isClosed()){
                connection.close();
                threadLocal.set(null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}