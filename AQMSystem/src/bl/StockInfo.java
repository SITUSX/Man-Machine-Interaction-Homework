package bl;

import model.Stock;
import vo.StockNewestInfoVO;

import java.util.List;

/**
 * Created by huangxiao on 2017/6/6.
 */
public interface StockInfo {

    Stock getSpecificStock(String code);

    List<StockNewestInfoVO> getNewestInfo(List<String> codes);

}
