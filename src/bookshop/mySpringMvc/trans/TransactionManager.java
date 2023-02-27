package bookshop.mySpringMvc.trans;




import bookshop.mySpringMvc.Util.ConnectionUtil;

import java.sql.SQLException;

/**
 * @author Alitar
 * @date 2023-02-01 19:49
 */
public class TransactionManager {

    //开启事务
    public static void beginTrans(){
        try {
            ConnectionUtil.getConn().setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //提交事务
    public static void commit(){

        try {
            ConnectionUtil.getConn().commit();
            ConnectionUtil.closeConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    //回滚事务
    public static void rollback(){

        try {
            ConnectionUtil.getConn().rollback();
            ConnectionUtil.closeConn();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}