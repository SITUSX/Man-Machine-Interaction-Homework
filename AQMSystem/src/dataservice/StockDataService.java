package dataservice;

import model.KData;
import model.Newest;
import model.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by huangxiao on 2017/3/9.
 */
public interface StockDataService {

    /**
     * 获取股票
     * @param code
     * @return Stock
     */
    Stock findStock(String code);

    /**
     * Get Stock File List
     *
     * @param filter : File name filter
     * @return 
     */
    List<Stock> searchStock(String filter);

    /**
     * 获得指定股票代码所有记录
     *
     * @param code ：股票代码
     * @return
     */
    List<KData> getStockRecords(String code);

    /**
     * 获得指定股票代码经过筛选后的股票记录
     *
     * @param code   ：股票代码
     * @return
     */
    List<KData> getStockRecords(String code, Date begin, Date end);

    /**
     * 获取指定日期市场行情信息
     *
     * @param date : 指定日期
     * @return 行情记录。[][2]， index 0 指当天， index 1 指前一天
     */
    List<KData[]> getMarketInfoByDate(Date date);

    /**
     * 获得最新的两条股票记录。
     *
     * @param code
     * @return 两条股票记录，Bean[0]:今天, Bean[1]:昨天
     */
    KData[] getNewestInfo(String code);

    /**
     * 获取股票实时行情
     * @param code
     * @return Newest
     */
    Newest findNewest(String code);

}
