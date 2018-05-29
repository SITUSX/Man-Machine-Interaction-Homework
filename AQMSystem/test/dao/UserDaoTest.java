package dao;

import dataservice.UserDataService;
import model.Stock;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangxiao on 2017/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml", "classpath:factory/bean_factory.xml"})
public class UserDaoTest {
    @Autowired
    private UserDataService userDataService;

    @Test
    public void testInsertUser() {
        User user = new User("hehuixian", "123456");
        Set<Stock> stockSet = new HashSet<>();

        user.setStocks(stockSet);
        userDataService.updateUser(user);
    }

    @Test
    public void testFindUser() {
        User user = userDataService.findUser("huangxiao");
        if (user != null) {
            System.out.println(user.getUsername() + '\t' + user.getPassword());
            for (Stock stock : user.getStocks()) {
                System.out.println(stock.getCode() + '\t' + stock.getName());
            }
        } else {
            System.out.println("" + null);
        }

    }

}
