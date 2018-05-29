package dao;

import dataservice.StockDataService;
import model.KData;
import model.Newest;
import model.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static config.Config.DATE_FORMAT;

/**
 * Created by huangxiao on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:web/WEB-INF/applicationContext.xml", "classpath:factory/bean_factory.xml"})
public class StockDaoTest {

    @Autowired
    private StockDataService stockDataService;

    @Test
    public void testFindStock() {
        Stock stock = stockDataService.findStock("000002");
        System.out.println(stock.getName());
    }

    @Test
    public void testGetStock() {
        List<Stock> stocks = stockDataService.searchStock("深");
        for (Stock stock : stocks) {
            System.out.println(stock.getCode() + " " + stock.getName());
        }
    }

    @Test
    public void testGetStockRecords() throws ParseException {
        Date begin = DATE_FORMAT.parse("2017-03-30");
        Date end = DATE_FORMAT.parse("2017-04-25");
        List<KData> kDataList = stockDataService.getStockRecords("002856", begin, end);
        for (KData kData : kDataList) {
            System.out.println(kData.getCode() + '\t'
                            + kData.getDate() + '\t'
                            + kData.getOpen() + '\t'
                            + kData.getHigh() + '\t'
                            + kData.getLow() + '\t'
                            + kData.getClose() + '\t'
                            + kData.getVolume() + '\t'
                            + kData.getAdjClose());
        }
    }

    @Test
    public void testGetMarketInfoByDate() throws ParseException {
        Date date = DATE_FORMAT.parse("2015-10-14");
        List<KData[]> kDatas = stockDataService.getMarketInfoByDate(date);
        for (KData[] kData : kDatas) {
            System.out.println(kData[0].getCode() + " " + kData[0].getDate() + "\t" + kData[1].getDate());
        }
    }

    @Test
    public void testGetNewestInfo() {
        KData[] kData = stockDataService.getNewestInfo("000002");
        System.out.println(kData[0].getCode() + "\t" + kData[0].getDate());
        System.out.println(kData[1].getCode() + "\t" + kData[1].getDate());
    }

    @Test
    public void testFindNewest() {
        Newest newest = stockDataService.findNewest("000401");
        System.out.println(newest.getName());
        System.out.println(newest.getChangePercent());
    }

}
