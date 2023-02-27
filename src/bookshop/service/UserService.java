package bookshop.service;

import bookshop.pojo.User;

/**
 * @author Alitar
 * @date 2023-02-17 17:14
 */
public interface UserService {
    User login(String uname,String pwd);
    void add(User user);
    User getUserName(String uname);

}
