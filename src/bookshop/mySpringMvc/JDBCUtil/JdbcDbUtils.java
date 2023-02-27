package bookshop.mySpringMvc.JDBCUtil;

import bookshop.mySpringMvc.Util.ConnectionUtil;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Alitar
 * @date 2022-12-24 17:06
 */
public abstract class JdbcDbUtils {
    private static String user = null;
    private static String password= null;
    private static String url= null;
    private static String driverClass= null;

    static {
        try {
            InputStream resourceAsStream = JdbcDbUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(resourceAsStream);

            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){

//        try {
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (SQLException throwable) {
//            System.out.println("connect database error...");
//            throwable.printStackTrace();
//        }

        return ConnectionUtil.getConn();
    }

    //数据库连接池C3p0获取连接方法
//    连接池只需要提供一个就可以了
    private static ComboPooledDataSource jdbcXml = new ComboPooledDataSource("JdbcXml");

    public static Connection getC3p0Connection(){

        Connection connection=null;
        try {
            connection = jdbcXml.getConnection();
        } catch (SQLException throwable) {
            System.out.println("connect database error...");
            throwable.printStackTrace();
        }
        return connection;
    }
//    获取dbcp连接池

    private static DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            InputStream is = JdbcDbUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");

            properties.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDbcpConnection(){
        Connection connection=null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwable) {
            System.out.println("connect database error...");
            throwable.printStackTrace();
        }
        return connection;
    }
    //    获取druid连接池
    static {
        try {
            Properties properties = new Properties();
            InputStream is = JdbcDbUtils.class.getClassLoader().getResourceAsStream("druid.properties");

            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDruidConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwable) {
            System.out.println("connect database error...");
            throwable.printStackTrace();
        }
        return connection;
    }
    public static  void CloseConnection(Connection connection,  Statement statement){
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
    }
    public static  void CloseSelect(Connection connection, Statement statement, ResultSet resultSet){
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(statement);
        DbUtils.closeQuietly(resultSet);
    }
}