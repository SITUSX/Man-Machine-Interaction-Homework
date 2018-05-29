package vo;

import model.Newest;

/**
 * Created by heleninsa on 2017/3/23.
 * <p>
 * 股票最新信息
 */
public class StockNewestInfoVO {
    public String stock_code;

    public String stock_name;

    public double open;

    public double high;

    public double low;

    public double trade;

    public double stock_range;

    public long volume;

    /**
     * PO to VO
     * @param newest
     */
    public StockNewestInfoVO(Newest newest) {
        this.stock_code = newest.getCode();
        this.stock_name = newest.getName();
        this.open = newest.getOpen();
        this.high = newest.getHigh();
        this.low = newest.getLow();
        this.trade = newest.getTrade();
        this.stock_range = newest.getChangePercent();
        this.volume = newest.getVolume();
    }

    public StockNewestInfoVO(String stock_code, String stock_name, double open, double high, double low, double trade, double stock_range, long volume) {
        this.stock_code = stock_code;
        this.stock_name = stock_name;
        this.open = open;
        this.high = high;
        this.low = low;
        this.trade = trade;
        this.stock_range = stock_range;
        this.volume = volume;
    }
}
