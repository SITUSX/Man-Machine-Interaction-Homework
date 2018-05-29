package vo;

import model.Stock;

/**
 * Created by huangxiao on 2017/6/2.
 */
public class StockVO {
    public String code;
    public String name;

    public StockVO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public StockVO(Stock stock) {
        this.code = stock.getCode();
        this.name = stock.getName();
    }
}
