package bl;

import blservice.UserBLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vo.StockNewestInfoVO;
import vo.StockVO;

import java.util.List;

/**
 * Created by huangxiao on 2017/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml", "classpath:factory/bean_factory.xml"})
public class UserBLTest {

    @Autowired
    private UserBLService userBLService;

    @Test
    public void testGetCollectStock() {
        List<StockVO> result = userBLService.getCollectStock("huangxiao");
        for (StockVO stockVO : result) {
            System.out.println(stockVO.code);
        }
    }

    @Test
    public void testPredictFavouriteStocks() {
        List<StockNewestInfoVO> result = userBLService.predictFavouriteStocks("huangxiao");
        for (StockNewestInfoVO stock : result) {
            System.out.println(stock.stock_code + "\t" + stock.stock_name + "\t" + stock.stock_range);
        }
    }

    @Test
    public void testIsCollected() {
        System.out.println(userBLService.isCollected("huangxiao", "000004"));
        System.out.println(userBLService.isCollected("huangxiao", "999999"));
    }

}
