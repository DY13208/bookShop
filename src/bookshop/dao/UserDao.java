package bookshop.dao;

import bookshop.pojo.User;

/**
 * @author Alitar
 * @date 2023-02-17 17:15
 */
public interface UserDao {
    User getUser(String uname,String password);
    void addUser(User user);
    User getUserName(String uname);
}
