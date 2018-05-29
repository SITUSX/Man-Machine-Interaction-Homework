package bl;

import blservice.StockBLService;
import blservice.StrategyBLService;
import config.BlockType;
import config.StrategyType;
import factory.BLFactory;
import org.junit.Test;
import vo.TestResultVO;

import java.text.ParseException;
import java.util.Date;

import static config.Config.DATE_FORMAT;

public class StrategyBLTest {

    private StrategyBLService meanStrategyBL = BLFactory.getStrategy(StrategyType.MEAN_REVERSION);
    private StrategyBLService momentumStrategy = BLFactory.getStrategy(StrategyType.MOMENTUM);
    private StrategyBLService minVarianceStrategy = BLFactory.getStrategy(StrategyType.MIN_VARIANCE);
    private StrategyBLService reverseStrategy = BLFactory.getStrategy(StrategyType.REVERSE);

    private StockBLService stockBLService = BLFactory.getBlService(StockBLService.class);

    @Test
    public void test() throws ParseException {
        Date begin = DATE_FORMAT.parse("2016-01-01");
        Date end = DATE_FORMAT.parse("2016-04-29");
//        List<StockVO> stockPool = stockBLService.getStockList("");
//        List<String> codePool = new ArrayList<>();
//        for (StockVO stockVO : stockPool) {
//            codePool.add(stockVO.code);
//        }
        long beginTime = System.currentTimeMillis();
        TestResultVO vo = minVarianceStrategy.backTest(BlockType.BLOCK_MAIN, begin, end, 5, 20);
        System.out.println("Mill cost:\t" + (System.currentTimeMillis() - beginTime));
        System.out.println("Alpha\t:" + vo.alpha);
        System.out.println("Beta\t:" + vo.beta);
        System.out.println("Sharpe\t:" + vo.sharpe);
        System.out.println("Drawdown:" + vo.maxDrawdown);
        System.out.println("Annual\t:" + vo.annualizedReturn);
        System.out.println("WunRate\t:" + vo.winRate);
    }

}
