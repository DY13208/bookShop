package bookshop.dao.imp;

import bookshop.dao.UserDao;
import bookshop.mySpringMvc.JDBCUtil.getDao;
import bookshop.pojo.User;


/**
 * @author Alitar
 * @date 2023-02-17 17:15
 */
public class UserDaoImp extends getDao<User> implements UserDao {

    @Override
    public User getUser(String uname, String password) {
        return load("select * from t_user where uname like  ? and pwd like  ?",uname,password);
    }

    @Override
    public void addUser(User user) {
          executeUpdate("INSERT INTO t_user values(0,?,?,?,0)",user.getUname(),user.getPwd(),user.getEmail());
    }

    @Override
    public User getUserName(String uname) {
        return load("select * from t_user where uname =?",uname);
    }
}