package dataservice;

import model.User;

/**
 * Created by huangxiao on 2017/5/31.
 */
public interface UserDataService {

    /**
     * 获取用户信息
     * @param username
     * @return User
     */
    User findUser(String username);

    /**
     * 添加用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

}
