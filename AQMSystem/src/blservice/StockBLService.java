package blservice;

import config.BlockType;
import vo.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by heleninsa on 2017/3/5.
 */
public interface StockBLService {

    /**
     * 获取股票列表
     *
     * @param keyword ： 搜索关键字
     * @return : 符合要求的股票 Code - Name
     */
    List<StockVO> getStockList(String keyword);

    /**
     * 根据条件获取股票代码
     * @param vo
     * @return
     */
    List<String> getStockByCondition(StockConditionVO vo);

    /**
     * 获取股票详情。对数收益率，方差，涨跌幅，开收，
     *
     * @param code
     * @return
     */
    StockDetailsVO getStockDetails(String code, Date from, Date to);

    /**
     * 获取股票最新信息
     * @param codes
     * @return
     */
    List<StockNewestInfoVO> getNewestInfo(List<String> codes);

    /**
     * 获取所有股票最新信息并排序选择
     * @param comparator
     * @param number
     * @return
     */
    List<StockNewestInfoVO> getNewestInfoByOrder(Comparator<StockNewestInfoVO> comparator, int number);

    /**
     * 获取所有股票市场行情
     * @param date
     * @return
     */
    MarketInfoVO getMarketInfo(Date date);

    /**
     * 获取某板块市场行情
     * @param date
     * @param blockType
     * @param blocks
     * @return
     */
    MarketInfoVO getMarketInfo(Date date, BlockType blockType, String... blocks);

}
