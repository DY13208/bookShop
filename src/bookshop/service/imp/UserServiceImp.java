package bookshop.service.imp;

import bookshop.dao.UserDao;
import bookshop.pojo.User;
import bookshop.service.UserService;

/**
 * @author Alitar
 * @date 2023-02-17 17:15
 */
public class UserServiceImp implements UserService {
    private UserDao userDao;

    @Override
    public User login(String uname, String pwd) {
        return userDao.getUser(uname, pwd);
    }

    @Override
    public void add(User user) {
        userDao.addUser(user);
    }

    @Override
    public User getUserName(String uname) {
        return userDao.getUserName(uname);
    }
}