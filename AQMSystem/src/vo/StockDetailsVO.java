package vo;

import model.KData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by heleninsa on 2017/3/8.
 */
public class StockDetailsVO {
    /**
     * 股票名称
     */
    public String stockName;
    /**
     * 股票信息列表
     */
    public List<KData> bean_list;
    /**
     * 最低价
     */
    public double minPrice;
    /**
     * 最高价
     */
    public double maxPrice;
    /**
     * 涨跌幅
     */
    public double change;
    /**
     * 总成交量
     */
    public long volume;
    /**
     * 收盘价
     */
    public Map<Date, Double> close;
    /**
     * 对数收益率
     */
    public Map<Date, Double> logReturn;
    /**
     * 对数收益率方差
     */
    public double varianceOfLogReturn;
    /**
     * 非交易日
     */
    public List<Date> dateException;


    /**
     * Default constructor
     */
    public StockDetailsVO() {
    }

    public StockDetailsVO(String stockName, List<KData> bean_list,
                          double minPrice, double maxPrice, double change, long volume,
                          Map<Date, Double> close, Map<Date, Double> logReturn,
                          double varianceOfLogReturn, List<Date> dateException) {
        this.stockName = stockName;
        this.bean_list = bean_list;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.change = change;
        this.volume = volume;
        this.close = close;
        this.logReturn = logReturn;
        this.varianceOfLogReturn = varianceOfLogReturn;
        this.dateException = dateException;
    }

}
