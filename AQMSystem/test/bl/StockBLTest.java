package bl;

import blservice.StockBLService;
import config.BlockType;
import factory.BLFactory;
import org.junit.Test;
import vo.MarketInfoVO;
import vo.StockConditionVO;
import vo.StockNewestInfoVO;
import vo.StockVO;

import java.text.ParseException;
import java.util.*;

import static config.Config.DATE_FORMAT;

public class StockBLTest {

    private StockBLService stockBLService = BLFactory.getBlService(StockBLService.class);

    @Test
    public void testGetStockList() {
        List<StockVO> stockList = stockBLService.getStockList("深");
        for (StockVO stock : stockList) {
            System.out.println(stock.code + " " + stock.name);
        }
    }

    @Test
    public void testGetMarketInfo() throws ParseException {
        Date date = DATE_FORMAT.parse("2015-10-14");
        MarketInfoVO vo = stockBLService.getMarketInfo(date, BlockType.BLOCK_INDUSTRY);
        System.out.println(vo.totalVolume);
        System.out.println(vo.totalDeclineLimit);
    }

    @Test
    public void testFindNewest() {
//        List<StockVO> stocks = stockBLService.getStockList("");
//        for (StockVO stock : stocks) {
//            StockNewestInfoVO vo = stockBLService.getNewestInfo(Collections.singletonList(stock.code)).get(0);
//            if (vo == null) {
//                System.out.println(stock.code + "\t" + stock.name);
//            }
//        }
        StockNewestInfoVO vo = stockBLService.getNewestInfo(Collections.singletonList("000001")).get(0);
        System.out.println(vo.stock_range);
    }

    @Test
    public void testGetStockByCondition() {
        int minVolume = 100000;
        int minChangePercent = 5;
        List<Boolean> sectionChoose = new ArrayList<>();
        sectionChoose.add(true);
        sectionChoose.add(false);
        sectionChoose.add(true);
        sectionChoose.add(false);
        StockConditionVO conditionVO = new StockConditionVO(sectionChoose, minVolume, minChangePercent);
        List<String> stocks = stockBLService.getStockByCondition(conditionVO);
        List<StockNewestInfoVO> stockNewestInfoVOS = stockBLService.getNewestInfo(stocks);
        for (StockNewestInfoVO vo : stockNewestInfoVOS) {
            System.out.println(vo.stock_code + '\t' + vo.stock_range + '\t' + vo.volume + '\t' + vo.trade);
        }
//        System.out.println(stocks.size());
    }

    @Test
    public void testGetNewestInfoByOrder() {
        List<StockNewestInfoVO> list = stockBLService.getNewestInfoByOrder(Comparator.comparingDouble(o -> o.stock_range), 10);
        list.forEach(s -> System.out.print(s.stock_code + "\t" + s.stock_name));
    }

}
