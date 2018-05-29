import blservice.BlockBLService;
import blservice.StockBLService;
import blservice.UserBLService;
import config.StrategyType;
import config.TimerManager;
import factory.BLFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Created by heleninsa on 2017/5/13.
 */
public class SpringTest {

    @Test
    public void testBeanInstance() {
        assertNotNull(BLFactory.getBlService(BlockBLService.class));
        assertNotNull(BLFactory.getBlService(StockBLService.class));
        assertNotNull(BLFactory.getBlService(UserBLService.class));
        assertNotNull(BLFactory.getStrategy(StrategyType.MOMENTUM));
        assertNotNull(BLFactory.getStrategy(StrategyType.MEAN_REVERSION));
        assertNotNull(BLFactory.getStrategy(StrategyType.REVERSE));
        assertNotNull(BLFactory.getStrategy(StrategyType.MIN_VARIANCE));
    }

    @Test
    public void testPath() {
        System.out.println(TimerManager.class.getResource("").getPath());
        System.out.println(System.getProperty("user.dir"));
    }

}
